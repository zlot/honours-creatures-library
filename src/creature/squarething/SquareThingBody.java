package creature.squarething;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.CreatePShape;
import creature.Creature;

public class SquareThingBody extends Body {

	public SquareThingBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		color = 0xFF00AAAA;
	}

	@Override
	public void setColor(int _color) {
		color = _color;
	}

	@Override
	protected PShape createBody() {
		PShape pShapeBody = CreatePShape.rect(0, 0, width, height);
		return pShapeBody;
	}

}
