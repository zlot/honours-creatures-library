package creature.squarething;

import processing.core.PVector;
import behaviour.CollisionBehaviour;
import behaviour.PBox2DBehaviour;
import behaviour.StayWithinWallsBehaviour;
import main.World;
import creature.Creature;

public class SquareThing extends Creature {

	public SquareThing() {
		pos = new PVector(p.random(World.getScreenWidth()), p.random(World.getScreenHeight()));
		createParts();
		addBehaviours();
	}

	@Override
	protected void createParts() {
		float r = p.random(15, 45);
		float r2 = p.random(15, 45);
		body = new SquareThingBody(this, pos, r, r);
	}

	@Override
	protected void addBehaviours() {
		addBehaviour(new PBox2DBehaviour(this));
//		addBehaviour(new CollisionBehaviour(this));
//		addBehaviour(new StayWithinWallsBehaviour(this));
	}

}
