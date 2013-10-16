package creature;

import java.util.ArrayList;
import java.util.List;

import loader.PClass;
import processing.core.PVector;

public abstract class Behaviour extends PClass {
	
	// TODO:: note: maybe have an nOffset, that gets incremented here?
	// and initiated with random value, allowing all behaviours
	// to always have an accessible noise value to use thats unique.

	protected Creature creature; // reference to creature instance
	private List<Updateable> updateables = new ArrayList<Updateable>();
	
	public Behaviour() {
////////////////// TEMP ONLY		
	}
	
	public Behaviour(Creature _creature) {
		creature = _creature;
	}
	
	/**
	 * Called by population director every frame.
	 */
	public abstract void update();
	
	/**
	 * Should be called by update(). This is a way of separating logic update
	 * code from movement code, to make the behaviour easier to understand.
	 * Note, your particular behaviour may not have update() and move() so easily separated. 
	 */
	protected abstract void move();
	
	
	
	
	
	
	// TODO:: ??
	public void updateUpdateables() {
		for(Updateable o : updateables) {
			o.update();
			o.draw();
		}
	}
	
	public List<Updateable> getUpdateables() {
		return updateables;
	}
	
	public void setCreature(Creature _creature) {
		creature = _creature;
	}
	

}
