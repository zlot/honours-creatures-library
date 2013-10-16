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
	    if (pos.x < - getBuffer())
	    	pos.x = getScreenWidthWithBuffer();
	    if (pos.y < - getBuffer())
	    	pos.y = getScreenHeightWithBuffer();
	    if (pos.x > getScreenWidthWithBuffer())
	    	pos.x = -getBuffer();
	    if (pos.y > getScreenHeightWithBuffer())
	    	pos.y = -getBuffer();
    }

	@Override
	protected void move() {

	}

}
