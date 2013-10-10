package creature.worm;

import behaviour.MoveBehaviourWithAng;
import processing.core.PVector;
import creature.Creature;

public class Worm extends Creature {
public float hithere;
	public Worm() {
		super();
		// somehow should force that pos must be set. Maybe via a thrown exception?
        pos = new PVector(p.random(getScreenWidth()), p.random(getScreenHeight()));
        createParts();
        addBehaviours();
	}

	@Override
	public void draw() {
		p.pushMatrix();
			p.translate(getPos().x, getPos().y);
			p.rotate(angle+p.radians(90));
			body.draw();
		p.popMatrix();
	}

	@Override
	protected void createParts() {
		// create body
		body = new WormBody(this, pos, 11, 180);
	}

	@Override
	protected void addBehaviours() {
		addBehaviour(new MoveBehaviourWithAng(this));
	}

}
