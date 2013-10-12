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
		
		// Ideally this will be easy to do direct from creature, 
		//   once Ollie's acc/vel request system is in place.
		float sineAngle = ((FeelerManager) creature.getLimbManager()).sineAngle;
		
		// *pi/2 to have the feelers facing the OPPOSITE direction of velocity.
		p.rotate(sineAngle+(creature.getVelocity().heading()*p.PI/2));
		//		p.rotate(p.radians(p.random(180))); // cool effect.
		
		p.pushStyle();
			p.stroke(color);
			p.strokeWeight(2);
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
