package creature.millipede;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;

public class MillipedeBody extends Body {
	
	public MillipedeBody() {
		super();
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
	protected synchronized PShape createBody() {
//		PShape bodyPShape = p.createShape(p.RECT, 0, 0, width, height);
		
		PShape bodyPShape = p.createShape();
		bodyPShape.beginShape();
		
		int gapBetweenFeelers = 8;
		// top left
		bodyPShape.vertex(-width/2,-height/2);
		// top right
		bodyPShape.vertex(width/2,-height/2);
		// iterate down
		for(int i=-(int)height/2; i<height/2; i+= gapBetweenFeelers)
			bodyPShape.vertex(width/2, i);
		// bottom left
		bodyPShape.vertex(-width/2,height/2);
		// iterate up
		for(int i=(int)height/2; i>-height/2; i-= gapBetweenFeelers)
			bodyPShape.vertex(-width/2, i);
		
		bodyPShape.endShape(p.CLOSE);
		return bodyPShape;
	}


}