package behaviour;

import main.World;
import processing.core.PVector;
import creature.Behaviour;
import creature.Creature;

public class WrapAroundBehaviour extends Behaviour {

	public WrapAroundBehaviour(Creature _creature) {
		super(_creature);
	}

	
	@Override
	public void update() {
		PVector pos = creature.getPos();
	    if (pos.x < -World.getBuffer())
	    	pos.x = World.getScreenWidthWithBuffer();
	    if (pos.y < -World.getBuffer())
	    	pos.y = World.getScreenHeightWithBuffer();
	    if (pos.x > World.getScreenWidthWithBuffer())
	    	pos.x = -getBuffer();
	    if (pos.y > World.getScreenHeightWithBuffer())
	    	pos.y = -World.getBuffer();
    }

	@Override
	protected void move() {

	}

}
