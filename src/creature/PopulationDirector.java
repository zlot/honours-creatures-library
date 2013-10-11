package creature;

import java.util.ArrayList;
import java.util.Map.Entry;

import loader.PClass;

public class PopulationDirector extends PClass {

	private static PopulationDirector singleton = new PopulationDirector(); // thread-safe.
	
	// List of all creatures in population
	public ArrayList<Creature> creatures;

	private PopulationDirector() {
		creatures = new ArrayList<Creature>();
	}
	
	public static PopulationDirector getInstance() {
		return singleton;
	}
	
	public void addCreature(Creature c) {
		creatures.add(c);
	}
	public void addCreatures(Class<? extends Creature> creatureClass, int numOfCreatures) {
		Creature c = null;
		for(int i=0; i<numOfCreatures; i++) {
			try {
				c = creatureClass.newInstance();
			} catch (Exception e) {e.printStackTrace();}
			creatures.add(c);
		}
	}
	
	public void update() {
		/* Update all behaviours */
		for(Creature c : creatures) {

			// iterate through Map of behaviours.
			for (Entry<Class<? extends Behaviour>, Behaviour> entry : c.getBehaviourManager().getBehaviours().entrySet()) {
				entry.getValue().update();
			}

			if(c.getLimbManager() != null) {
				c.getLimbManager().update(); // update limbs. This may or may not do anything
												 // depending on the implementation of the limb.
			}
		}
		/* Draw all creatures */
		for(Creature c : creatures) {
			c.draw();
		}
	}
	
	public void setBodyForAllCreaturesOfClass(Class<?> creatureClass, Class<?> body) {
		ArrayList<Creature> creaturesOfTypeCreatureClass = new ArrayList<Creature>();
		// iterate through creatures list: find all creatures of creatureClass.
		for(Creature c : creatures) {
			if(c.getClass().isAssignableFrom(creatureClass)) {
				// place these in a new list.
				creaturesOfTypeCreatureClass.add(c);
			}
		}
		// for all of these found creatureClass, call setBody(body).
		for(Creature c : creaturesOfTypeCreatureClass) {
			try {
				Body bodyInstance = (Body) body.newInstance();
				c.setBody(bodyInstance);
			} catch (Exception ex) {ex.printStackTrace();}
		}
	}
	
	public void setLimbManagerForAllCreaturesOfClass(Class<?> creatureClass, Class<?> limbManager) {
		ArrayList<Creature> creaturesOfTypeCreatureClass = new ArrayList<Creature>();
		// iterate through creatures list: find all creatures of creatureClass.
		for(Creature c : creatures) {
			if(c.getClass().isAssignableFrom(creatureClass)) {
				// place these in a new list.
				creaturesOfTypeCreatureClass.add(c);
			}
		}
		// for all of these found creatureClass, call setLimbManager(limbManager).
		for(Creature c : creaturesOfTypeCreatureClass) {
			try {
				LimbManager limbManagerInstance = (LimbManager) limbManager.newInstance();
				c.setLimbManager(limbManagerInstance);
			} catch (Exception ex) {ex.printStackTrace();}
		}
		
	}
}
