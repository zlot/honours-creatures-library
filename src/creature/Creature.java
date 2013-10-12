package creature;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import behaviour.PBox2DBehaviour;
import processing.core.PVector;
import loader.PClass;

public abstract class Creature extends PClass {

	protected PVector pos = null; // will point to same pos instance as body pos.
	protected PVector vel = null;
	protected PVector acc = null;
	
	protected float angle; // angle creature is facing, in radians.
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
	
	// run implicitly inside PopulationDirector?
	protected void update() {
		vel.add(acc);
		// vel.limit(10); // might need this as a field, and accessor/setter method
		pos.add(vel);
		
		 // get angle of pos to vel (this is in radians) for angle creature is moving.
	    setAngle(vel.heading());		
		
		// set acc back to 0 for next update (). http://natureofcode.com/book/chapter-2-forces/
		acc.mult(0);
	}
	
	/**
	 * Convenience function. Equivalent of calling add(Behaviour b) on behaviourManager.
	 */
	protected void addBehaviour(Behaviour b) {
		b.setCreature(this);
		behaviourManager.add(b);
	}
	/**
	 * Convenience function. Equivalent of calling remove(Behaviour b) on behaviourManager.
	 */
	protected void removeBehaviour(Behaviour b) {
		// behaviours is a map.
		behaviourManager.remove(b);
	}
	
	public void setBody(Body _body) {
		body = _body;
		// re-establish limbs to attach to new body.
		limbManager.getLimbs().clear();
		limbManager.createLimbs();
		// re-establish behaviours (they might depend on new body)
		reInitialiseBehaviours();
	}
	
	private void reInitialiseBehaviours() {
		Map<Class<? extends Behaviour>, Behaviour> behaviours = getBehaviourManager().getBehaviours();
		
		for (Entry<Class<? extends Behaviour>, Behaviour> entry : getBehaviourManager().getBehaviours().entrySet()) {
			
			Class<? extends Behaviour> behaviourClass = entry.getKey();
			
			try {
//				p.println(behaviourClass.toString());
				Constructor<? extends Behaviour> behaviourConstructor = behaviourClass.getDeclaredConstructor(new Class[] {creature.Creature.class});
				behaviours.put(entry.getKey(), behaviourConstructor.newInstance(this));
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}
	
	public void setLimbManager(LimbManager _limbManager) {
		_limbManager.createLimbs();
		synchronized(limbManager.getLimbs()) {
			limbManager = _limbManager;
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
