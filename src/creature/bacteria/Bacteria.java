package creature.bacteria;

import behaviour.MoveBehaviourWithAng;
import processing.core.PVector;
import creature.Creature;

public class Bacteria extends Creature {
	
	public Bacteria() {
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
			limbManager.draw();
		p.popMatrix();
	}

	@Override
	protected void createParts() {
		// create body
		body = new BacteriaBody(this, pos, 80, 180);
		
		// create feelerManager.
		limbManager = new FeelerManager(this);
	}

	@Override
	protected void addBehaviours() {
//		addBehaviour(new MoveBehaviourWithAng(this));
	}

}
