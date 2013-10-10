package pbox2d;

import java.lang.reflect.Method;

import main.World;

import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import behaviour.CollisionBehaviour2;
import creature.Creature;
import processing.core.PApplet;

public class PContactListener implements ContactListener {
	PApplet parent;
	
	Method addMethod;
	Method persistMethod;
	Method removeMethod;
	Method resultMethod;
	
	PContactListener(PApplet p){
		parent = p;
		
		// for each creature, check if CollisionBehaviour2 exists.
		// then, check if each of these methods exist (they will).
		
		// then, fix up each listener/invoker method below so they run off the CollisionBehaviour2 class.
		
		for(Creature c : World.populationDirector.creatures) {
			if(c.getBehaviourManager().getBehaviours().containsKey(CollisionBehaviour2.class)) {
				
				Class collisionBehaviour2Class = c.getBehaviourManager().getBehaviours().get(CollisionBehaviour2.class).getClass();
				
				try {
					addMethod = collisionBehaviour2Class.getMethod("addContact", new Class[] { ContactPoint.class });
				} catch (Exception e) {
					System.out.println("You are missing the addContact() method. " + e);
				}
				
				try {
					persistMethod = collisionBehaviour2Class.getMethod("persistContact", new Class[] { ContactPoint.class });
				} catch (Exception e) {
					System.out.println("You are missing the persistContact() method. " + e);
				}
				try {
					removeMethod = collisionBehaviour2Class.getMethod("removeContact", new Class[] { ContactPoint.class });
				} catch (Exception e) {
					System.out.println("You are missing the removeContact() method. " + e);
				}
				try {
					resultMethod = collisionBehaviour2Class.getMethod("resultContact", new Class[] { ContactResult.class });
				} catch (Exception e) {
					System.out.println("You are missing the resultContact() method. " + e);
				}
			}
		}
		
		

        

	}

	// This function is called when a new collision occurs
	public void add(ContactPoint cp) {
        if (addMethod != null) {
            try {
            	addMethod.invoke(parent, new Object[] { cp });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"addContact()\" method for some reason.");
                e.printStackTrace();
                addMethod = null;
            }
        }
	}


	 // Contacts continue to collide - i.e. resting on each other
	public void persist(ContactPoint cp)  {
        if (persistMethod != null) {
            try {
            	persistMethod.invoke(parent, new Object[] { cp });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"persistContact()\" method for some reason.");
                e.printStackTrace();
                persistMethod = null;
            }
        }
	}

	// Objects stop touching each other
	public void remove(ContactPoint cp)  {
        if (removeMethod != null) {
            try {
            	removeMethod.invoke(parent, new Object[] { cp });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"removeContact()\" method for some reason.");
                e.printStackTrace();
                removeMethod = null;
            }
        }
	}

	// Contact point is resolved into an add, persist etc
	public void result(ContactResult cr) {
        if (resultMethod != null) {
            try {
            	resultMethod.invoke(parent, new Object[] { cr });
            } catch (Exception e) {
                System.out.println("Could not invoke the \"resultContact()\" method for some reason.");
                e.printStackTrace();
                resultMethod = null;
            }
        }

	}


}
