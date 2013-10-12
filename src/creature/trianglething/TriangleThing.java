package creature.trianglething;

import behaviour.PBox2DBehaviour;
import processing.core.PVector;
import creature.Creature;
import creature.bacteria.FeelerManager;
import creature.virus.TentacleManager;

public class TriangleThing extends Creature {

	public TriangleThing() {
		super();
		setPos(new PVector(p.random(getScreenWidth()), p.random(getScreenHeight())));
		createParts();
		addBehaviours();
	}
	
	@Override
	protected void createParts() {
		float randomWidthAndHeight = p.random(20, 40);
		body = new TriangleThingBody(this, new PVector(p.width/2, p.height/2), randomWidthAndHeight, randomWidthAndHeight);
		limbManager = new FeelerManager(this); // w/2 is radius
	}

	@Override
	protected void addBehaviours() {
		addBehaviour(new PBox2DBehaviour(this));
	}

}
