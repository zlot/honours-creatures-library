package creature.bacteria;

import java.util.ArrayList;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;

public class BacteriaBody extends Body {
	

	
	
	public BacteriaBody() {
		super();
		setColor(0xea00aa22);

	}
	
	public BacteriaBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setColor(0xea00aa22);
	}
	
	public void createBody() {
	    // The "original" locations of the vertices make up a circle
		vertices = new ArrayList<PVector>();
	    for (float a = 0; a < p.TWO_PI; a+=0.2) {
	      PVector v = PVector.fromAngle(a);
	      v.mult(width);
	      vertices.add(v);
	    }	    
	    
	    // Now make the PShape with those vertices
      	bodyPShape = p.createShape();
      	bodyPShape.beginShape();
      	bodyPShape.fill(34,160,200);
      	bodyPShape.stroke(0);
      	bodyPShape.strokeWeight(2);
////////////////////////////////////
      	bodyPShape.curveDetail(12); // default is 20 (from processing javadoc)
      	
      	
		for(int i=0;i<4;i++)
			wiggle();
      	
      	
	    for (PVector v : vertices) {
////////////////////////////////////
	    	bodyPShape.curveVertex(v.x, v.y);
	    }
	    bodyPShape.endShape(p.CLOSE);
      
	}

	@Override
	public void draw() {
		p.pushStyle();
//			p.rectMode(p.CENTER);
			p.noStroke();
			p.fill(0xffee33ee); // color
			p.shape(bodyPShape);
//			p.rect(0,0,width,height);
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