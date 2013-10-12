package creature.trianglething;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.CreatePShape;
import creature.Creature;

public class TriangleThingBody extends Body {

	public TriangleThingBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setColor(p.color(330, 79, 88));
	}

	@Override
	protected PShape createBody() {
		return CreatePShape.triangle(0, 0, width, height);
	}

}