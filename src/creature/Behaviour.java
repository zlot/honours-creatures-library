package creature;

import java.util.ArrayList;
import java.util.List;

import loader.PClass;
import processing.core.PVector;


/**
 * Mediator pattern?
 *
 */
public abstract class Behaviour extends PClass {
	
	// note: maybe have an nOffset, that gets incremented here?
	// and initiated with random value, allowing all behaviours
	// to always have an accessible noise value to use thats unique.

	protected Creature creature; // reference to creature instance
	private List<Updateable> updateables = new ArrayList<Updateable>();
	
	
	// behaviour should register existence to BehaviourDirector.
	// observer pattern. BehaviourDirector controlls this.
	// but maybe we need a mediator or proxy here? A BehaviourManager?
	// so the Creature itself gets control over switching its behaviour?
	public Behaviour(Creature _creature) {
		creature = _creature;
	}
	
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
	
	public abstract void update();
	
	protected abstract void move();

//	public abstract void moveTo(PVector loc);

	public abstract void startMove();
	
	public abstract void stopMove();
	
	public abstract void freeze();	

	
}
