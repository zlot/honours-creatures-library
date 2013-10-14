package creature;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import processing.core.PVector;
import loader.PClass;

public abstract class Creature extends PClass {

	/***** STEERING BEHAVIOUR *****/
	protected PVector pos = null; // will point to same pos instance as body pos.
	protected PVector vel = null;
	protected PVector acc = null;
	
	protected float angle; // angle creature is facing, in radians. 0 is facing east.
	
	/***** PARTS *****/
	protected Body body = null;
	protected LimbManager limbManager = null;
	
	protected BehaviourManager behaviourManager = null;
	
	/**
	 * Constructor of subclass must call setPos(), createParts() and addBehaviours()!
	 */
	public Creature() {
		vel = new PVector(0,0);
		acc = new PVector(0,0);
		behaviourManager = new BehaviourManager(this);
	}
	
	protected abstract void createParts();
	
	protected abstract void addBehaviours();
	
	public void draw() {
		p.pushMatrix();
			p.translate(getPos().x, getPos().y);
			p.rotate(angle);
			body.draw();
			if(limbManager != null) limbManager.draw();
		p.popMatrix();
	}
	
	/**
	 * run every draw loop inside PopulationDirector.
	 */
	protected void update() {
		
		updateMovement();
		// set acc back to 0 for next update (). http://natureofcode.com/book/chapter-2-forces/
		acc.mult(0);
		
		updateBehaviours();
		updateLimbs();
		
	}
	
	private void updateMovement() {
		body.update();
		
		vel.add(acc);
		// vel.limit(10); // might need this as a field, and accessor/setter method
		pos.add(vel);
		
		 // get angle of pos to vel (this is in radians) for angle creature is moving.
	    setAngle(vel.heading());
	}
	
	private void updateBehaviours() {
		// iterate through Map of behaviours.
		for (Entry<Class<? extends Behaviour>, Behaviour> entry : behaviourManager.getBehaviours().entrySet()) {
			entry.getValue().update();
		}
	}
	
	private void updateLimbs() {
		if(limbManager != null) {
			limbManager.update();  // update limbs. This may or may not do anything, depending on implementation of limb.
		}
	}
	

	public void setBody(Body _body) {
		body = _body;
		// re-establish limbs to attach to new body.
		if(limbManager != null) {
			limbManager.getLimbs().clear();
			limbManager.createLimbs();
		}
		// re-establish behaviours (as they might depend on new body)
		reInitialiseBehaviours();
	}
	
	public void setLimbManager(LimbManager _limbManager) {
		_limbManager.getLimbs().clear();
		_limbManager.createLimbs();
		
		if(limbManager == null) {
			// don't synchonize, as there is no limbs array yet.
			limbManager = _limbManager;
		} else {
			// else, synchronize access.
			synchronized(limbManager.getLimbs()) {
				limbManager = _limbManager;
			}
		}
		reInitialiseBehaviours();
	}
	
	
	/**
	 * Convenience function. Equivalent of calling add(Behaviour b) on behaviourManager.
	 */
	protected void addBehaviour(Behaviour behaviour) {
		behaviour.setCreature(this);
		behaviourManager.add(behaviour);
	}
	/**
	 * Convenience function. Equivalent of calling remove(Behaviour b) on behaviourManager.
	 */
	protected void removeBehaviour(Behaviour behaviour) {
		// behaviours is a map.
		behaviourManager.remove(behaviour);
	}
	/**
	 * Convenience function. Equivalent of calling hasBehaviour() on behaviourManager.
	 */	
	public boolean hasBehaviour(Class<? extends Behaviour> behaviour) {
		return behaviourManager.hasBehaviour(behaviour);
	}
	
	private void reInitialiseBehaviours() {
		Map<Class<? extends Behaviour>, Behaviour> behaviours = getBehaviourManager().getBehaviours();
		
		for (Entry<Class<? extends Behaviour>, Behaviour> entry : getBehaviourManager().getBehaviours().entrySet()) {
			Class<? extends Behaviour> behaviourClass = entry.getKey();
			try {
				Constructor<? extends Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
				behaviours.put(entry.getKey(), behaviourConstructor.newInstance(this));
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}
	
	
	public Body getBody() {
		return body;
	}
	public LimbManager getLimbManager() {
		return limbManager;
	}
	public BehaviourManager getBehaviourManager() {
		return behaviourManager;
	}
	public void setPos(PVector _pos) {
		pos = _pos;
	}
	public void setPos(float x, float y) {
		pos = new PVector(x, y);
	}
	public PVector getPos() {
		return pos;
	}
	public void setVelocity(PVector _vel) {
		vel = _vel;
	}
	public PVector getVelocity() {
		return vel;
	}
	public void setAcceleration(PVector _acc) {
		acc = _acc;
	}
	
/////////////////////////// TODO::
	//////////////////////// MAYBE change this to addForce??
	public void addAcceleration(PVector force) {
		acc.add(force);
	}
	
	public PVector getAcceleration() {
		return acc;
	}
	
	
	public float getAngle() {
		return angle;
	}
	public void setAngle(float _angle) {
		angle = _angle;
	}
	
	public boolean hasLimbManager() {
		return limbManager == null ? false : true;
	}
	
}
