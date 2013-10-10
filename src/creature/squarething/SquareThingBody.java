package creature.squarething;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;

public class SquareThingBody extends Body {

	public SquareThingBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		color = 0xFF00AAAA;
	}

	@Override
	public void draw() {
		p.pushStyle();
		p.stroke(color);
		p.noFill();
		p.rectMode(p.CENTER);
		p.rect(0, 0, getWidth(), getHeight());
		p.popStyle();
	}

	@Override
	public void setColor(int _color) {
		color = _color;
	}

	@Override
	protected PShape createBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
