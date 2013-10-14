package behaviour;

import main.World;
import processing.core.PVector;
import creature.Behaviour;
import creature.Creature;

public class StayWithinWallsBehaviour extends Behaviour {

	public StayWithinWallsBehaviour(Creature _creature) {
		super(_creature);
	}

	
	@Override
	public void update() {
		// https://github.com/shiffman/The-Nature-of-Code-Examples/blob/master/Processing/chp6_agents/NOC_6_03_StayWithinWalls/Vehicle.pde
		
		//[offset-down] Make a desired vector that retains the y direction of
		  // the vehicle but points the x direction directly away from
		  // the window’s left edge.
		
	    PVector desired = null;
	    
	    PVector pos = creature.getPos();
	    PVector velocity = creature.getVelocity();
	    //// implement a maxspeed in creature? and maxforce?
	    float maxspeed = 10;
	    float maxforce = 1; // 0.01?

	    if (pos.x < World.getBuffer()) {
	      desired = new PVector(maxspeed, velocity.y);
	    } 
	    else if (pos.x > World.getScreenWidth() - World.getBuffer()) {
	      desired = new PVector(-maxspeed, velocity.y);
	    } 

	    if (pos.y < World.getBuffer()) {
	      desired = new PVector(velocity.x, maxspeed);
	    } 
	    else if (pos.y > World.getScreenHeight() - World.getBuffer()) {
	      desired = new PVector(velocity.x, -maxspeed);
	    } 

	    if (desired != null) {
	      desired.normalize();
	      desired.mult(maxspeed);
	      PVector steer = PVector.sub(desired, velocity);
	      steer.limit(maxforce);
	      creature.addAcceleration(steer);
//	      applyForce(steer);
	    }
    }

	@Override
	protected void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void freeze() {
		// TODO Auto-generated method stub

	}

}
