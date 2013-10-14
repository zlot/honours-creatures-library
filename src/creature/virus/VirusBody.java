package creature.virus;

import processing.core.*;
import creature.Body;
import creature.CreatePShape;
import creature.Creature;

public class VirusBody extends Body {
	
	public VirusBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setFillColor(0xFF007744);
	}

	@Override
	public void draw() {
		p.pushStyle();
		p.shape(bodyPShape);
		p.popStyle();
	}

	@Override
	protected synchronized PShape createBody() {
		PShape bodyPShape = CreatePShape.circle(0, 0, width/2, 30);
		bodyPShape.setStroke(0);
		bodyPShape.setStrokeWeight(2);
	    return bodyPShape;
	}


}
