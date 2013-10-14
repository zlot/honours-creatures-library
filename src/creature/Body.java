package creature;

import java.util.ArrayList;

import processing.core.PShape;
import processing.core.PVector;

public abstract class Body extends Part {

	protected float width, height;
	
	// note: to modify bodyPShape vertexes, must use bodyPShape.setVertex(i, PVector).
	// can't modify a PVector v = bodyPShape.getVertex(i) for example.
	// This looks like it must just make a copy! not a direct pointer to the vertex INSIDE bodyPShape.
	protected PShape bodyPShape;
	// We are using an ArrayList to keep a duplicate copy
	// of vertices original locations.
	protected ArrayList<PVector> vertices = new ArrayList<PVector>();
	
	AABB aabb; // aabb for body. needs: farthest top, right, bottom, left values. how to get these?
	
	public Body(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos);
		width = _width;
		height = _height;
		setAABB(_width, _height);
		
		setBodyPShape(createBody());
		
		assert(bodyPShape != null) : "You have not correctly initialised and returned "
				+ "a bodyPShape in createBody()! To create a simple shape, e.g. an ellipse, use "
				+ "the static methods in the CreatePShape class.";
		
		if(vertices.isEmpty()) {
			// fill vertices with bodyPShape vertices.
			for(int i=0;i<bodyPShape.getVertexCount();i++)
				vertices.add(bodyPShape.getVertex(i));
		}
	}
	
	public void draw() {
		p.pushStyle();
		p.shape(bodyPShape);
		p.popStyle();
	}

	
	/**
	 * Optional Override 
	 */
	public void update() {
		// Optional Override.
	}
	
////////////////////////////////
//////////////////////////////
	//////////////////////////
	////////////// TEMP ONLY. testing with scale!
	float setScale;
	
	public void setScale(float newScaleValue) {
		if(setScale == newScaleValue)
			return;
		bodyPShape.scale(newScaleValue);
		setScale = newScaleValue;
	}
	
	public void setScale(float scaleXValue, float scaleYValue) {
		bodyPShape.scale(scaleXValue, scaleYValue);
	}
	
//////////////////////////////
//////////////////////////////
//////////////////////////
////////////// TEMP ONLY. testing with setSCale!(see above)	
	
	public void setWidth(float _width) {
		width = _width;
		// re-create bodyPShape?
		createBody();
	}
	public void setHeight(float _height) {
		height = _height;
	}
	public void setAABB(float width, float height) {
		PVector lowerVertex = new PVector(-width/2, height/2);
		PVector upperVertex = new PVector(width/2, -height/2);
		aabb = new AABB(lowerVertex, upperVertex);
	}
	
	protected abstract PShape createBody();
	
	public PShape getBodyPShape() {
		return bodyPShape;
	}
	private void setBodyPShape(PShape _bodyPShape) {
		bodyPShape = _bodyPShape;
	}
	
	public void setFillColor(int color) {
		super.setFillColor(color);
		bodyPShape.setFill(color);
	}
	public void setStrokeColor(int color) {
		bodyPShape.setStroke(color);
	};
	public int getStrokeColor() {
		return bodyPShape.getStroke(0);
	}
	
	public ArrayList<PVector> getVertices() {
		if(vertices.isEmpty()) {
			for(int i=0;i<bodyPShape.getVertexCount();i++) {
				vertices.add(bodyPShape.getVertex(i));
			}
		}
		return vertices;
	}
	
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	public AABB getAABB() {
		return aabb;
	}
	
}
