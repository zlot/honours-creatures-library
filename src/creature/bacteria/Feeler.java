package creature.bacteria;

import behaviour.MoveBehaviourWithAng;
import processing.core.PVector;
import creature.Creature;
import creature.Limb;
import toxi.math.waves.*;

public class Feeler extends Limb {

	
	public Feeler(Creature _creature, PVector _pos, float _width, float _angle) {
		super(_creature, _pos);
		width = _width;
		setColor(p.color(p.random(70),80,80, 95));
		angle = _angle;
	}

	@Override
	public void draw() {
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		
		setAngle(creature.getAngle());
		p.rotate(-(angle+p.radians(90))); // rotate back to world axis
		
		// the following is all temporary. Ideally this will be easy to do direct from creature 
		// once Ollie's acc/vel request system is in place.
		
///////////// NOTE::: THIS WONT WORK BECAUSE DEPENDENT ON BEHAVIOUR. WILL NEED TO FIX THIS!!
//		MoveBehaviourWithAng m = (MoveBehaviourWithAng) creature.getBehaviourManager().getBehaviours().get(MoveBehaviourWithAng.class);
		
//		float ang2 = m.ang2;
		float ang2 = 0;
		
		float sineAngle = ((FeelerManager) creature.getLimbManager()).sineAngle;
		p.rotate(ang2+sineAngle+p.radians(90));

		
		//		p.rotate(p.radians(p.random(180))); // cool effect.
		
		p.pushStyle();
			p.stroke(color);
			p.strokeWeight(2);
			//p.line(pos.x, pos.y, pos.x+width, pos.y);
			p.line(0, 0, 0, width);
			
		p.popStyle();
		p.popMatrix();
	}

	@Override
	public void update() {
	}

	@Override
	public void setColor(int _color) {
		color = _color;
	}
	
	public void setFeelerLength(float length) {
		width = length;
	}

}
