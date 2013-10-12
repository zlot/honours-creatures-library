package creature.worm;

import behaviour.MoveBehaviourWithAng;
import processing.core.PVector;
import creature.Creature;

public class Worm extends Creature {
	
	public Worm() {
		super();
		// somehow should force that pos must be set. Maybe via a thrown exception?
        setPos(new PVector(p.random(getScreenWidth()), p.random(getScreenHeight())));
        createParts();
        addBehaviours();
	}

	@Override
	protected void createParts() {
		// create body
		body = new WormBody(this, pos, 180, 11);
	}

	@Override
	protected void addBehaviours() {
		addBehaviour(new MoveBehaviourWithAng(this));
	}

}
