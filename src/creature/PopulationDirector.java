package creature;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map.Entry;

import processing.core.PApplet;
import loader.PClass;

public class PopulationDirector extends PClass {

	private static PopulationDirector singleton = new PopulationDirector(); // thread-safe.
	
	public ArrayList<Creature> creatures; // List of all creatures in population.

	private PopulationDirector() {
		creatures = new ArrayList<Creature>();
	}
	
	public static PopulationDirector getInstance() {
		return singleton;
	}
	
	public void addCreature(Creature c) {
		String creatureName = c.getClass().getName();
		String realCreatureName = creatureName.substring(creatureName.indexOf('$')+1);
		
		// assertions to make sure developer has coded creature correctly.
		if(c.pos == null)
			PApplet.println("You have not called setPos() for your creature: " + realCreatureName
					+ ". Please call setPos() in " + realCreatureName + "'s constructor, after super()!");
		
		if(c.body == null)
			PApplet.println("You have not set a body for your creature: " + realCreatureName
					+ ". Please set " + realCreatureName + "'s body variable, inside " + realCreatureName + ".createParts()!");
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

//////////////////// TODO::: can I push draw() into the one loop yet? Or will that still break stuff?		
		
		/* Update all behaviours */
		for(Creature creature : creatures) {
			creature.update();
		}

		/* Draw all creatures */
		for(Creature creature : creatures) {
//			c.update();
			creature.draw();
		}
	}
	
	
	/**
	 * Utility function for TweakMode tool only. Not for your use!
	 */
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

	/**
	 * Utility function for TweakMode tool only. Not for your use!
	 */
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
			if(limbManager == null) {
				LimbManager nullLimbManger = null;
				c.setLimbManager(nullLimbManger);
			} else {
				try {
					Class<LimbManager> limbManagerClass = limbManager;
					Constructor<LimbManager> limbManagerConstructor = limbManagerClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
					LimbManager limbManagerInstance = limbManagerConstructor.newInstance(c);
					c.setLimbManager(limbManagerInstance);
				} catch (Exception ex) {ex.printStackTrace();}
			}
		}	
	}

	/**
	 * Utility function for TweakMode tool only. Not for your use!
	 */
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
	
	/**
	 * Utility function for TweakMode tool only. Not for your use!
	 */	
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
