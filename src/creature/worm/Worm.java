package creature.worm;

import behaviour.MoveBehaviourWithAng;
import processing.core.PVector;
import creature.Creature;
import creature.bacteria.FeelerManager;

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
		
		// create limb manager
		limbManager = new FeelerManager(this);
	}

	@Override
	protected void addBehaviours() {
		addBehaviour(new MoveBehaviourWithAng(this));
	}

}
