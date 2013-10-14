package behaviour;

import creature.Behaviour;
import creature.Creature;
import processing.core.*;
import loader.PClass;
import main.World;

public class MoveBehaviourRandom extends Behaviour {

	float nOffset; // special offset to make virus movement unique.

	public MoveBehaviourRandom(Creature _creature) {
		super(_creature);
	}
	
	// noise-walk the pos
	public void move() {
	    PVector pos = creature.getBody().getPos();
		
		PVector force = seek(new PVector(p.random(World.getScreenWidth()), p.random(World.getScreenHeight())));
		
	    // update() in Creature does the rest.
	    creature.addAcceleration(force);
	    

	    if(pos.x > World.getScreenWidthWithBuffer()) {
	      pos.x = 0;
	    } else if (pos.x < -World.getBuffer()) {
	      pos.x = World.getScreenWidthWithBuffer();
	    }
	    
	    if(pos.y > World.getScreenHeightWithBuffer()) {
	      pos.y = 0;
	    } else if (pos.y < -World.getBuffer()) {
	      pos.y = World.getScreenHeightWithBuffer();
	    }
	    
    }

	@Override
	public void update() {
		move();
	}
	
	// Our seek steering force algorithm
	private PVector seek(PVector target) {
		float maxSpeed = 115;
		float maxForce = 0.05f;
		
		// where it wants to go
	    PVector desired = PVector.sub(target, creature.getBody().getPos());
	    desired.normalize();
	    desired.mult(maxSpeed);
	    // steer towards this desired position, looking at the current velocity and applying force.
	    PVector steer = PVector.sub(desired, creature.getVelocity());
	    steer.limit(maxForce);
	    return steer;
	}


}
