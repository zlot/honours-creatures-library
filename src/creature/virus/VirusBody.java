package creature.virus;

import processing.core.*;
import creature.Body;
import creature.Creature;

public class VirusBody extends Body {
	
	
	public VirusBody() {
		super();
		setColor(0xFF007744);
//		bodyPShape.setFill(0xFF007744);

	}
	
	public VirusBody(Creature _creature, PVector _pos, int _width, int _height) {
		super(_creature, _pos, _width, _height);
		setColor(0xFF007744);
////////////////////////////
		bodyPShape.setFill(0xFF007744);

	}

	@Override
	public void draw() {
		// ellipse, with pos at center
		//p.fill(0, 80, 80);
		p.pushStyle();
		p.shape(bodyPShape);
		p.fill(color);
//		p.ellipse(0, 0, width, width);
		p.popStyle();
	}

	@Override
	protected void createBody() {
		bodyPShape = p.createShape();
		bodyPShape.beginShape();
		bodyPShape.fill(color);
		bodyPShape.stroke(0);
		bodyPShape.strokeWeight(2);
	    for (float a = 0; a < p.TWO_PI; a+=0.2) {
	      PVector v = PVector.fromAngle(a);
	      v.mult(width/2);
	      bodyPShape.vertex(v.x, v.y);
	    }
	    bodyPShape.endShape(p.CLOSE);
	    
	}


}
