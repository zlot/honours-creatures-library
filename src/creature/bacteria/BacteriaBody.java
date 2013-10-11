package creature.bacteria;

import java.util.ArrayList;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;

public class BacteriaBody extends Body {

	public BacteriaBody() {
		super();
	}
	
	public BacteriaBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setColor(p.color(34,160,200));
	}
	
	@Override
	protected synchronized PShape createBody() {
	    // The "original" locations of the vertices make up a circle
		vertices = new ArrayList<PVector>();
	    for (float a = 0; a < p.TWO_PI; a+=0.2) {
	      PVector v = PVector.fromAngle(a);
	      v.mult(width);
	      vertices.add(v);
	    }	    
	    
	    // Now make the PShape with those vertices
      	PShape _bodyPShape = p.createShape();
      	_bodyPShape.beginShape();
      	_bodyPShape.stroke(0);
      	_bodyPShape.strokeWeight(2);
      	_bodyPShape.curveDetail(12); // default is 20 (from processing javadoc)
      	
		for(int i=0;i<4;i++)
			wiggle();
      	
	    for (PVector v : vertices) {
	    	_bodyPShape.curveVertex(v.x, v.y);
	    }
	    _bodyPShape.endShape(p.CLOSE);
	    
	    return _bodyPShape;
	}

	@Override
	public void draw() {
		p.pushStyle();
			p.noStroke();
			p.shape(bodyPShape);
		p.popStyle();
	}


	  // For 2D Perlin noise
	  float yoff = 0;


	  void wiggle() {
	    float xoff = 0;
	    // Apply an offset to each vertex
	    for (int i = 0; i < vertices.size(); i++) {
	      // Calculate a new vertex location based on noise around "original" location
	      PVector pos = vertices.get(i);
	      float a = p.TWO_PI*p.noise(xoff,yoff);
	      PVector r = PVector.fromAngle(a);
	      r.mult(2);
	      r.add(pos);
	      // Set the location of each vertex to the new one
	      //bodyPShape.setVertex(i, r.x, r.y);
	      pos.set(r.x, r.y);
	      
	      
	      // increment perlin noise x value
	      xoff+= 0.5;
	    }
	    // Increment perlin noise y value
	    yoff += 0.02;
	  }


}