package creature;

import java.util.HashMap;
import java.util.Map;

import loader.PClass;

public class BehaviourManager extends PClass {

	protected Creature creature; // reference to creature that BehaviourManager belongs to.
	
	// Map of behaviours. Implemented via a HashMap.
	protected Map<Class<? extends Behaviour>, Behaviour> behaviours = new HashMap<Class<? extends Behaviour>, Behaviour>();
	
	public BehaviourManager(Creature _c) {
		creature = _c;
	}
	
	public void add(Behaviour b) {
		behaviours.put(b.getClass(), b);
	}
	
	public Map<Class<? extends Behaviour>, Behaviour> getBehaviours() {
		return behaviours;
	}
}
