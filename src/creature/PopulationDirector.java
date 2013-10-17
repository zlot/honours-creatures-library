package creature;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map.Entry;

import processing.core.PApplet;
import processing.core.PVector;
import loader.PClass;

public class PopulationDirector extends PClass {

	private static PopulationDirector singleton = new PopulationDirector(); // thread-safe.
	
	private ArrayList<Creature> creatures; // List of all creatures in population.

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
		// optional override.
	}
	
	
	public void internalUpdate() {

//////////////////// TODO::: can I push draw() into the one loop yet? Or will that still break stuff?		
		
		/* Update all behaviours */
		for(Creature creature : creatures) {
			creature.update();
		}

		/* Draw all creatures */
		for(Creature creature : creatures) {
//			creature.update();
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
			
			boolean constructedClassSuccessfully = false;
			
			try {
				Class<Body> bodyClass = body;
				Constructor<Body> bodyConstructor = null;
				Body bodyInstance = null;
				
				/***** A NOTE about getDeclaredConstructor()::: *****/
				/*** Returns a Constructor object that reflects the specified public constructor of the class represented by this Class object.
				 * The parameterTypes parameter is an array of Class objects that identify the constructor's formal parameter types, in declared order.
				 * If this Class object represents an inner class declared in a non-static context, the formal parameter types include the explicit
				 * enclosing instance as the first parameter.
				 * Because all classes created in Processing are inner classes of PApplet, we must prepend a PApplet class to the Constructor.
				 * AND also, when using Constructor.newInstance():
				 * If the constructor's declaring class is an inner class in a non-static context,
				 * -------> the first argument to the constructor needs to be the enclosing instance.
				 * http://stackoverflow.com/questions/2097982/is-it-possible-to-create-an-instance-of-nested-class-using-java-reflection/2098082#2098082
				 */
				try {
					bodyConstructor = bodyClass.getDeclaredConstructor(new Class[] {creature.Creature.class, PVector.class, float.class, float.class});
					bodyInstance = bodyConstructor.newInstance(c, c.getPos(), c.getBody().getWidth(), c.getBody().getHeight());
					constructedClassSuccessfully = true;
				} catch(NoSuchMethodException ex) {
					// Must be a user-created class inside Processing.. Try the next getDeclaredConstructor!
				}
				if(!constructedClassSuccessfully) {
					try {
					    Class<?> worldofcreaturesClass = bodyClass.getDeclaredConstructors()[0].getParameterTypes()[0];
						bodyConstructor = bodyClass.getDeclaredConstructor(new Class[] {worldofcreaturesClass, creature.Creature.class, PVector.class, float.class, float.class});
						bodyInstance = bodyConstructor.newInstance(p, c, c.getPos(), c.getBody().getWidth(), c.getBody().getHeight());
						constructedClassSuccessfully = true;
					} catch(NoSuchMethodException ex) {
						// Somethings deffo wrong.
					}
				}
				
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
			
			boolean constructedClassSuccessfully = false;
			
			if(limbManager == null) {
				LimbManager nullLimbManger = null;
				c.setLimbManager(nullLimbManger);
			} else {
				
				
				try {
					Class<LimbManager> limbManagerClass = limbManager;
					
					Constructor<LimbManager> limbManagerConstructor = null;
					LimbManager limbManagerInstance = null;
					
					
					try {
						limbManagerConstructor = limbManagerClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
						limbManagerInstance = limbManagerConstructor.newInstance(c);
						constructedClassSuccessfully = true;
					} catch(NoSuchMethodException ex) {
						// Must be a user-created class inside Processing.. Try the next getDeclaredConstructor!
					}
					if(!constructedClassSuccessfully) {
						try {
						    Class<?> worldofcreaturesClass = limbManagerClass.getDeclaredConstructors()[0].getParameterTypes()[0];
						    limbManagerConstructor = limbManagerClass.getDeclaredConstructor(new Class[] {worldofcreaturesClass, creature.Creature.class});
						    limbManagerInstance = limbManagerConstructor.newInstance(p, c);
						    
						    p.println(limbManagerInstance);
							constructedClassSuccessfully = true;
						} catch(Exception ex) {
							// Somethings deffo wrong.
							ex.printStackTrace();
						}
					}
					
					
					c.setLimbManager(limbManagerInstance);
					
					
				} catch (Exception ex) {ex.printStackTrace();}
			}
		}	
	}

	/**
	 * Utility function for TweakMode tool only. Not for your use!
	 */
	@SuppressWarnings("static-access")
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
			
			boolean constructedClassSuccessfully = false;
			
			try {
				
				Class<Behaviour> behaviourClass = behaviour;
				Behaviour behaviourInstance = null; 
				
				try {
					Constructor<Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
					
					
					behaviourInstance = behaviourConstructor.newInstance(c);
					constructedClassSuccessfully = true;
				} catch(NoSuchMethodException ex) {
				}
				
				if(!constructedClassSuccessfully) {
					try {
					    Class<?> worldofcreaturesClass = behaviourClass.getDeclaredConstructors()[0].getParameterTypes()[0];
					    
					    Constructor<Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {worldofcreaturesClass, creature.Creature.class});
					
						behaviourInstance = behaviourConstructor.newInstance(p, c);
						
						behaviourInstance.setCreature(c);
						constructedClassSuccessfully = true;
						
					} catch(Exception ex) {
					}
				}
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
			
			boolean constructedClassSuccessfully = false;
			
			try {
				
				Class<Behaviour> behaviourClass = behaviour;
				Behaviour behaviourInstance = null;           
				
				try {
					Constructor<Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
					
					
					behaviourInstance = behaviourConstructor.newInstance(c);
					constructedClassSuccessfully = true;
				} catch(NoSuchMethodException ex) {
				}
				
				if(!constructedClassSuccessfully) {
					try {
					    Class<?> worldofcreaturesClass = behaviourClass.getDeclaredConstructors()[0].getParameterTypes()[0];
					    
					    Constructor<Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {worldofcreaturesClass, creature.Creature.class});
					
						behaviourInstance = behaviourConstructor.newInstance(p, c);
						
						behaviourInstance.setCreature(c);
						constructedClassSuccessfully = true;
						
					} catch(Exception ex) {
					}
				}
				
				c.removeBehaviour(behaviourInstance);
				
			} catch (Exception ex) {ex.printStackTrace();}
		}	
		
	}
	
	public ArrayList<Creature> getCreatures() {
		return creatures;
	}
	
	
}
