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
	    PVector desired = null;

	    PVector pos = creature.getPos();
	    PVector velocity = creature.getVelocity();
	    //// implement a maxspeed in creature? and maxforce?
	    float speed = 8;

	    // if out of range on x axis, go in opposite direction at maximum speed.
	    // and keep the y velocity the same.
	    if (pos.x < World.getBuffer()) {
	      desired = new PVector(speed, velocity.y);
	    } 
	    else if (pos.x > getScreenWidth() - getBuffer()) {
	      desired = new PVector(-speed, velocity.y);
	    } 
	    // if out of range on y axis, go in opposite direction at maximum speed.
	    // and keep the x velocity the same.
	    if (pos.y < World.getBuffer()) {
	      desired = new PVector(velocity.x, speed);
	    } 
	    else if (pos.y > getScreenHeight() - getBuffer()) {
	      desired = new PVector(velocity.x, -speed);
	    } 
	    
	    if (desired != null) {
	    	creature.moveInDirection(desired);
	    }
    }

	@Override
	protected void move() {

	}

}
