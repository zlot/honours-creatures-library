package creature;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import processing.core.PVector;
import loader.PClass;

/**
 *  Used as base for your own creature.
 *  Please note that the constructor of your subclassed creature must call setPos(), createParts() and addBehaviours().
 */
public abstract class Creature extends PClass {

	/***** STEERING BEHAVIOUR *****/
	protected PVector pos = null; // will point to same pos instance as body pos.
	protected PVector vel = null;
	protected PVector acc = null;
	
	private float speed;	// a desired speed.
	private float maxForce; // maximum steering force. How fast can the creature turn?
	private float maxSpeed; // how fast does the creature try to move?
	
	
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
		
		speed = 10; // generic speed. Can be overridden by your creature.
		
		maxForce = 0.15f; // generic, arbitrarily set maxForce.
		
		// maxSpeed set at 0, meaning maxSpeed is set by behaviours.
		// if maxSpeed is set by your creature, creature will always move at this maximum speed,
		// regardless of what behaviours set maxSpeed to.
		maxSpeed = 0;
		
		behaviourManager = new BehaviourManager(this);
	}
	
	/**
	 * Must be called in Constructor. body and limbManager must be set here.
	 */
	protected abstract void createParts();
	
	/**
	 * Must be called in Constructor. Add behaviours in here.
	 * Call addBehaviour() to add a behaviour. Format is:
	 * 
	 * addBehaviour(new <BehaviourName>(this));
	 * 
	 */
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
	protected final void update() {
/////////////////////////////
		
		updateMovement();
		// set acc back to 0 for next update (). http://natureofcode.com/book/chapter-2-forces/
		acc.mult(0);
		
		updateBehaviours();
		updateLimbs();
		
	}
	
	/***** MOVEMENT / FORCE METHODS *****/
	
	/**
	 * Steers creature towards target.
	 * @param desiredTarget target in sketch coordinates.
	 */
	public void moveToTarget(PVector desiredTarget) {
		PVector desiredTargetLocalised = PVector.sub(desiredTarget, pos);
		desiredTargetLocalised.normalize();
		PVector steer = PVector.sub(desiredTargetLocalised, vel); // // Steering is desired minus velocity.
		steer.limit(maxForce); // determines how quick it can steer in direction of desiredTarget. 
		addForce(steer);
	}
	
	/**
	 * Steers creature towards target.
	 * Used by behaviours to set their own specific maxForce.
	 * @param desiredTarget target in sketch coordinates.
	 * @param behaviourMaxForce specific to behaviour.
	 */
	public void moveToTarget(PVector desiredTarget, float behaviourMaxForce) {
		PVector desiredTargetLocalised = PVector.sub(desiredTarget, pos);
		desiredTargetLocalised.normalize();
		desiredTargetLocalised.mult(speed); // scale desiredTarget up by maxSpeed.
		PVector steer = PVector.sub(desiredTargetLocalised, vel); // // Steering is desired minus velocity.
		steer.limit(behaviourMaxForce); // determines how quick it can steer in direction of desiredTarget. 
		addForce(steer);
	}
	
	/**
	 * Steers creature towards target.
	 * Used by behaviours to set their own specific maxForce and maxSpeed.
	 * @param desiredTarget target in sketch coordinates.
	 * @param behaviourMaxSpeed specific to behaviour.
	 * @param behaviourMaxForce specific to behaviour.
	 */
	public void moveToTarget(PVector desiredTarget, float behaviourMaxSpeed, float behaviourMaxForce) {
		PVector desiredTargetLocalised = PVector.sub(desiredTarget, pos);
		desiredTargetLocalised.normalize();
		desiredTargetLocalised.mult(behaviourMaxSpeed); // scale desiredTarget up by maxSpeed.
		PVector steer = PVector.sub(desiredTargetLocalised, vel); // // Steering is desired minus velocity.
		steer.limit(behaviourMaxForce); // determines how quick it can steer in direction of desiredTarget. 
		addForce(steer);
	}

	/**
	 * Steers creature towards a direction, relative to the creature's position.
	 * @param desiredDirection direction to move. Vector is normalised and multiplied by behaviourMaxSpeed.
	 */
	public void moveInDirection(PVector desiredDirection) {
		desiredDirection.normalize();
		desiredDirection.mult(speed); // scale desiredTarget up by maxSpeed.
		PVector steer = PVector.sub(desiredDirection, vel); // // Steering is desired minus velocity.
		steer.limit(maxForce); // determines how quick it can steer in direction of desiredTarget. 
		addForce(steer);
	}
	
	/**
	 * Steers creature towards a direction, relative to the creature's position.
	 * Used by behaviours to set their own specific maxForce.
	 * @param desiredDirection direction to move. Vector is normalised and multiplied by behaviourMaxSpeed.
	 * @param behaviourMaxForce specific to behaviour.
	 */
	public void moveInDirection(PVector desiredDirection, float behaviourMaxForce) {
		desiredDirection.normalize();
		desiredDirection.mult(speed); // scale desiredTarget up by maxSpeed.
		PVector steer = PVector.sub(desiredDirection, vel); // // Steering is desired minus velocity.
		steer.limit(behaviourMaxForce); // determines how quick it can steer in direction of desiredTarget. 
		addForce(steer);
	}
	
	/**
	 * Steers creature towards a direction, relative to the creature's position.
	 * Used by behaviours to set their own specific maxForce and maxSpeed.
	 * @param desiredDirection direction to move. Vector is normalised and multiplied by behaviourMaxSpeed.
	 * @param behaviourMaxSpeed specific to behaviour.
	 * @param behaviourMaxForce specific to behaviour.
	 */
	public void moveInDirection(PVector desiredDirection, float behaviourMaxSpeed, float behaviourMaxForce) {
		desiredDirection.normalize();
		desiredDirection.mult(behaviourMaxSpeed); // scale desiredTarget up by maxSpeed.
		PVector steer = PVector.sub(desiredDirection, vel); // // Steering is desired minus velocity.
		steer.limit(behaviourMaxForce); // determines how quick it can steer in direction of desiredTarget. 
		addForce(steer);
	}
	
	/**
	 * Add a force to manipulate movement of creature.
	 * @param force force applied from a behaviour.
	 */
	public void addForce(PVector force) {
		acc.add(force);
	}
	/**
	 * Convenience function. Does exactly the same thing as addForce(PVector force);
	 */
	public void addAcceleration(PVector _acc) {
		addForce(_acc);
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
	
	
	/***** GETTERS *****/
	/**
	 * @return body of creature.
	 */
	public Body getBody() {
		return body;
	}
	/**
	 * @return limbManager of creature. Will be null if not set.
	 */
	public LimbManager getLimbManager() {
		return limbManager;
	}
	/**
	 * @return behaviourManager of creature.
	 */
	public BehaviourManager getBehaviourManager() {
		return behaviourManager;
	}
	/**
	 * @return position of creature.
	 */
	public PVector getPos() {
		return pos;
	}
	/**
	 * @return velocity of creature.
	 */
	public PVector getVelocity() {
		return vel;
	}
	public PVector getAcceleration() {
		return acc;
	}
	public float getSpeed() {
		return speed;
	}	
	/**
	 * @return the maximum speed set for the creature.
	 * 		   0 means the maximum speed is set by behaviours.
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}
	/**
	 * @return the turning force of the creature.
	 */
	public float getMaxForce() {
		return maxForce;
	}
	/**
	 * @return the angle the creature is orientated at, in radians.
	 *         0 is facing east.
	 */
	public float getAngle() {
		return angle;
	}
	
	
	
	/***** SETTERS *****/
	/**
	 * @param _pos new position of creature.
	 */
	public void setPos(PVector _pos) {
		pos = _pos;
	}
	public void setPos(float x, float y) {
		pos = new PVector(x, y);
	}
	public void setVelocity(PVector _vel) {
		vel = _vel;
	}
	public void setAcceleration(PVector _acc) {
		acc = _acc;
	}
	public void setSpeed(float _speed) {
		speed = _speed;
	}
	/**
	 * Set this to provide a global maxSpeed for this creature, which all behaviours must abide by.
	 */
	public void setMaxSpeed(float _maxSpeed) {
		maxSpeed = _maxSpeed;
	}
	/**
	 * @param _maxForce usually the maximum turning speed of a creature.
	 */
	public void setMaxForce(float _maxForce) {
		maxForce = _maxForce;
	}
	/**
	 * @param _angle the angle which the creature is orientated, in radians. 0 is facing east.
	 */
	public void setAngle(float _angle) {
		angle = _angle;
	}
	
	
	
	public boolean hasLimbManager() {
		return limbManager == null ? false : true;
	}

	
	
/* USED BY INTERFACE */
	
	
	
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
		if(_limbManager != null) {
			_limbManager.getLimbs().clear();
			_limbManager.createLimbs();
		}
		if(_limbManager == null || limbManager == null) {
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
	
	
	/***** PRIVATE METHODS *****/
	/**
	 * Called by update().
	 */
	private void updateMovement() {
		body.update();
		
		vel.add(acc);
		
		// limit to creature maxSpeed if this has been set,
		// Otherwise, limited only by maxSpeed set by behaviours.
		if(maxSpeed > 0) vel.limit(maxSpeed);
		
		pos.add(vel);
		
		// get angle of pos to vel (this is in radians) for angle creature is moving.
		setAngle(vel.heading());
	}
	
	/**
	 * Called by update(). 
	 */
	private void updateBehaviours() {
		// iterate through Map of behaviours.
		for (Entry<Class<? extends Behaviour>, Behaviour> entry : behaviourManager.getBehaviours().entrySet()) {
			entry.getValue().update();
		}
	}
	/**
	 * Called by update(). 
	 */
	private void updateLimbs() {
		if(limbManager != null) {
			limbManager.update();  // update limbs. This may or may not do anything, depending on implementation of limb.
		}
	}
	
	/**
	 * Called by setBody().
	 */
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

}
