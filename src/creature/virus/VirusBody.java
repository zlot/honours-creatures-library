package creature.virus;

import processing.core.*;
import creature.Body;
import creature.Creature;

public class VirusBody extends Body {
	
	public VirusBody() {
		super();

	}
	
	public VirusBody(Creature _creature, PVector _pos, int _width, int _height) {
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
		_bodyPShape.endShape(p.CLOSE);
		_bodyPShape = p.createShape(p.ELLIPSE, 0, 0, width, height);
		
		
//	    for (float a = 0; a < p.TWO_PI; a+=0.2) {
//	      PVector v = PVector.fromAngle(a);
//	      v.mult(width/2);
//	      _bodyPShape.vertex(v.x, v.y);
//	    }
	    return _bodyPShape;
	}


}
