package creature;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import loader.PClass;

/**
 * Helper class which can create body PShapes based off basic shapes.
 * @author Mark C Mitchell
 */
public class CreatePShape extends PClass {

	public static PShape rect(float x, float y, float width, float height) {
		PShape bodyPShape = p.createShape();
		bodyPShape.beginShape();
		// top left
		bodyPShape.vertex(-width/2,-height/2);
		// top right
		bodyPShape.vertex(width/2,-height/2);
		// bottom right
		bodyPShape.vertex(width/2, height/2);
		// bottom left
		bodyPShape.vertex(-width/2,height/2);
		bodyPShape.endShape(p.CLOSE);
		
		return bodyPShape;
	}
	
	/**
	 * Creates a perfect circle PShape. Will create the circle with x,y as center.
	 * 
	 * @param x x position relative to origin(0,0).
	 * @param y y position relative to origin(0,0).
	 * @param radius radius of circle.
	 * @param detail vertices per degree. In degrees.
	 * @return ready-to-use perfect circle PShape for body.
	 */
	public static PShape circle(float x, float y, float radius, float detailInDegrees) {
		PShape bodyPShape = p.createShape();
		bodyPShape.beginShape();
		
	    for (float theta = 0; theta < PApplet.TWO_PI; theta+=PApplet.radians(detailInDegrees)) {
	    	PVector v = PVector.fromAngle(theta);
	    	v.mult(radius);
	    	bodyPShape.vertex(v.x + x, v.y + y);
	    }
	    
		bodyPShape.endShape(PApplet.CLOSE);
		
		return bodyPShape;
	}

	
	/**
	 * Creates an ellipse PShape. Will create an ellipse with x,y as center.
	 * 
	 * @param x x position relative to origin(0,0).
	 * @param y y position relative to origin(0,0).
	 * @param radius radius of circle.
	 * @param detail vertices per degree. In degrees.
	 * @return ready-to-use ellise PShape for body.
	 */
	public static PShape ellipse(float x, float y, float width, float height, float detailInDegrees) {
		PShape bodyPShape = p.createShape();
		bodyPShape.beginShape();
		
	    for (float theta = 0; theta < PApplet.TWO_PI; theta+=PApplet.radians(detailInDegrees)) {
	    	float ellipseX = width/2 * PApplet.cos(theta);
	    	float ellipseY = height/2 * PApplet.sin(theta);
	    	
	    	bodyPShape.vertex(ellipseX + x, ellipseY + y);
	    }
	    
		bodyPShape.endShape(PApplet.CLOSE);
		
		return bodyPShape;
	}

	
	/**
	 * Triangle facing right (0 degrees). Triangle is created with x,y as center.
	 * 
	 * @param x x position relative to origin(0,0).
	 * @param y y position relative to origin(0,0).
	 * @param width width of triangle.
	 * @param height height of triangle.
	 * @return ready-to-use triangle PShape for body.
	 */
	public static PShape triangle(float x, float y, float width, float height) {
		PShape bodyPShape = p.createShape();
		bodyPShape.beginShape();
		
		// far right point 
		bodyPShape.vertex(width/2, 0);
		// bottom-left point
		bodyPShape.vertex(-width/2, height/2);
		// top-left point
		bodyPShape.vertex(-width/2, -height/2);
	    
		bodyPShape.endShape(PApplet.CLOSE);
		
		return bodyPShape;
	}
}
