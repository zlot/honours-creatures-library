package creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PVector;
import creature.bacteria.Feeler;
import loader.PClass;

public abstract class LimbManager extends PClass {

	protected Creature creature; // reference to creature that LimbManager belongs to.
	
	// synchronized. Remember, must also synchronize block iterating over list!
	protected List<Limb> limbs = Collections.synchronizedList(new ArrayList<Limb>());
	
	
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
	
	/**
	 * OPTIONAL: override this in LimbManager implementation if limbs require logic for updating.
	 */
	public void update() {
		// optional override
	};
	
	/**
	 * Write in this method the logic to create and attach Limb's to your creature.
	 */
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
