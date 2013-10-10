package creature;

import java.util.ArrayList;

import processing.core.PShape;
import processing.core.PVector;

public abstract class Body extends Part {

	protected float width, height;
	
	protected PShape bodyPShape;
	// We are using an ArrayList to keep a duplicate copy
	// of vertices original locations.
	protected ArrayList<PVector> vertices = new ArrayList<PVector>();
	
	AABB aabb; // aabb for body. needs: farthest top, right, bottom, left values. how to get these?
	
	public Body() {
		super();
		// MUST BE SET IF THIS CONSTRUCTOR IS USED.
		// THIS CONSTRUCTOR MEANT TO BE USED ONLY FOR TWEAK MODE RUNTIME STUFF.
		width = -1;
		height = -1;
	}
	public Body(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos);
		width = _width;
		height = _height;
		setAABB(_width, _height);
		createBody();
	}
	
	public abstract void draw();
	
	public void setWidth(float _width) {
		width = _width;
	}
	public void setHeight(float _height) {
		height = _height;
	}
	public void setAABB(float width, float height) {
		
///////////////////////////////////////
///////////////////////////////////////
///////////////////////////////////////
//////// COULD USE bodyPShape.getWidth(), bodyPShape.getHeight()?
		/////////// note Javadoc for this says "gets width of drawing area. Not necessarily shape boundary".
		PVector lowerVertex = new PVector(-width/2, height/2);
		PVector upperVertex = new PVector(width/2, -height/2);
		aabb = new AABB(lowerVertex, upperVertex);
	}
	
///////////////////////////////////////
///////////////MUST IMPLEMENT THIS IN ALL OTHER CREATURES.
	protected abstract void createBody();
	
	public PShape getBodyPShape() {
		return bodyPShape;
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
