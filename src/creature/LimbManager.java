package creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import loader.PClass;

public abstract class LimbManager extends PClass {

	protected Creature creature; // reference to creature that LimbManager belongs to.
	
	// synchronized. Remember, must also synchronize block iterating over list!
	protected List<Limb> limbs = Collections.synchronizedList(new ArrayList<Limb>());
	
	
	public LimbManager() {
		// MUST BE SET IF THIS CONSTRUCTOR IS USED.
		// THIS CONSTRUCTOR MEANT TO BE USED ONLY FOR TWEAK MODE RUNTIME STUFF.
		creature = null;
		limbs.clear();
	}
	
	public LimbManager(Creature _c) {
		creature = _c;
	}
	
	public void draw() {
		synchronized(limbs) {
			for(Limb limb : limbs) {
				limb.draw();
			}
		}
	}	
	public void update() {
		// optional override
	};
	
	public abstract void createLimbs();
	
	public void setCreature(Creature _c) {
		creature = _c;
	}
	
	public Creature getCreature() {
		return creature;
	}
	
	public void add(Limb l) {
		limbs.add(l);
	}
	
	public List<Limb> getLimbs() {
		return limbs;
	}
}
