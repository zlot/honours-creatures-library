package creature.virus;

import processing.core.*;
import creature.Body;
import creature.Creature;

public class VirusBody extends Body {
	
	public VirusBody() {
		super();
	}
	
	public VirusBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setColor(0xFF007744);
	}

	@Override
	public void draw() {
		p.pushStyle();
		p.shape(bodyPShape);
		p.popStyle();
	}

	@Override
	protected synchronized PShape createBody() {
		PShape _bodyPShape = p.createShape();
	
		_bodyPShape.beginShape();
		_bodyPShape.stroke(0);
		_bodyPShape.strokeWeight(2);

////////// TODO:: DOESNT WORK!! SHOULD STILL CREATE MY OWN CREATESHAPE CLASS.		
//		PShape _bodyPShape = p.createShape(PApplet.ELLIPSE, 0, 0, width, height);
		
	    for (float a = 0; a < p.TWO_PI; a+=0.2) {
	      PVector v = PVector.fromAngle(a);
	      v.mult(width/2);
	      _bodyPShape.vertex(v.x, v.y);
	    }
		_bodyPShape.endShape(p.CLOSE);
		
	    return _bodyPShape;
	}


}
