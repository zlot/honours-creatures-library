package creature.millipede;

import behaviour.MoveBehaviour;
import behaviour.MoveBehaviourRandom;
import behaviour.MoveBehaviourWithAng;
import behaviour.PBox2DBehaviour;
import processing.core.PVector;
import creature.Creature;

public class Millipede extends Creature {
	
	public Millipede() {
		super();
		// somehow should force that pos must be set. Maybe via a thrown exception?
        pos = new PVector(p.random(getScreenWidth()), p.random(getScreenHeight()));
        createParts();
        addBehaviours();
	}


	@Override
	protected void createParts() {
		// create body
		body = new MillipedeBody(this, pos, 180, 11);
		
		// create feelerManager.
		limbManager = new creature.bacteria.FeelerManager(this);
	}

	@Override
	protected void addBehaviours() {
		addBehaviour(new MoveBehaviourRandom(this));
//		addBehaviour(new PBox2DBehaviour(this));
	}

}
