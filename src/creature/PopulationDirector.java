package creature;

import java.lang.reflect.Constructor;
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
////////////////////////////
//////////////////////// NOTE: HAVE SWAPPED AROUND ORDER OF THINGS:
		//////////////// creatures update
		//////////////// THEN behaviours get to change the standard order of things
		//////////////// THEN creatures draw. This is so PBox2D works better. might break other things.
		
		/* Update all behaviours */
		for(Creature c : creatures) {

			c.update();
			// iterate through Map of behaviours.
			for (Entry<Class<? extends Behaviour>, Behaviour> entry : c.getBehaviourManager().getBehaviours().entrySet()) {
				entry.getValue().update();
			}

			if(c.getLimbManager() != null) {
				c.getLimbManager().update(); // update limbs. This may or may not do anything
												 // depending on the implementation of the limb.
			}
		}

		/* Update all creatures */
		/* Draw all creatures */
		for(Creature c : creatures) {
//			c.update();
			c.draw();
		}
	}
	
	public void setBodyForAllCreaturesOfClass(Class<Creature> creatureClass, Class<Body> body) {
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
				Class<Body> bodyClass = body;
				Constructor<Body> bodyConstructor = bodyClass.getDeclaredConstructor(new Class[] {creature.Creature.class, processing.core.PVector.class, float.class, float.class});
				Body bodyInstance = bodyConstructor.newInstance(c, c.getPos(), c.getBody().getWidth(), c.getBody().getHeight());
				c.setBody(bodyInstance);
			} catch (Exception ex) {ex.printStackTrace();}
		}
	}
	
	public void setLimbManagerForAllCreaturesOfClass(Class<Creature> creatureClass, Class<LimbManager> limbManager) {
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
				LimbManager limbManagerInstance = limbManager.newInstance();
				c.setLimbManager(limbManagerInstance);
			} catch (Exception ex) {ex.printStackTrace();}
		}	
	}
	
	public void addBehaviourForAllCreaturesOfClass(Class<Creature> creatureClass, Class<Behaviour> behaviour) {
		ArrayList<Creature> creaturesOfTypeCreatureClass = new ArrayList<Creature>();
		// iterate through creatures list: find all creatures of creatureClass.
		for(Creature c : creatures) {
			if(c.getClass().isAssignableFrom(creatureClass)) {
				// place these in a new list.
				creaturesOfTypeCreatureClass.add(c);
			}
		}
		// for all of these found creatureClass, call addBehaviour(b).
		for(Creature c : creaturesOfTypeCreatureClass) {
			try {
				Class<Behaviour> behaviourClass = behaviour;
				Constructor<Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
				Behaviour behaviourInstance = behaviourConstructor.newInstance(c);
				c.addBehaviour(behaviourInstance);
			} catch (Exception ex) {ex.printStackTrace();}
		}	
		
	}
	public void removeBehaviourForAllCreaturesOfClass(Class<Creature> creatureClass, Class<Behaviour> behaviour) {
		ArrayList<Creature> creaturesOfTypeCreatureClass = new ArrayList<Creature>();
		// iterate through creatures list: find all creatures of creatureClass.
		for(Creature c : creatures) {
			if(c.getClass().isAssignableFrom(creatureClass)) {
				// place these in a new list.
				creaturesOfTypeCreatureClass.add(c);
			}
		}
///////////////// NOTE::: TODO::
		///////// double looping here. Could avoid using the creaturesOfTypeCreatureClass array, and just throw it all
		///////// in the first, full iteration of creatures.
		////////  this is for ALL fo these methods in this class!!
		
		// for all of these found creatureClass, call removeBehaviour(b).
		for(Creature c : creaturesOfTypeCreatureClass) {
			try {
				Class<Behaviour> behaviourClass = behaviour;
				Constructor<Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
				Behaviour behaviourInstance = behaviourConstructor.newInstance(c);
				c.removeBehaviour(behaviourInstance);
			} catch (Exception ex) {ex.printStackTrace();}
		}	
		
	}
	
	
}
