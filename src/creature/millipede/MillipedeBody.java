package creature.millipede;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;

public class MillipedeBody extends Body {
	
	public MillipedeBody() {
		super();
		setColor(0xea00aa22);
	}
	
	public MillipedeBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setColor(0xea00aa22);
	}

	@Override
	public void draw() {
		p.pushStyle();
			p.shape(bodyPShape);
			
		p.popStyle();
	}

	@Override
	protected PShape createBody() {
//		PShape bodyPShape = p.createShape(p.RECT, 0, 0, width, height);
//		PShape bodyPShape = p.createShape(p.ELLIPSE, 0, 0, width, height);
		bodyPShape.setFill(0xea00aa22);
		return bodyPShape;
	}


}