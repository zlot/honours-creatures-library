package creature;

import processing.core.PVector;
import loader.PClass;

public abstract class Creature extends PClass {

	protected PVector pos = null; // will point to same pos instance as body pos.
	protected float angle; // angle creature is facing, in radians.
	protected Body body = null;
	protected LimbManager limbManager = null;
	
	protected BehaviourManager behaviourManager = null;
	
	public Creature() {
		behaviourManager = new BehaviourManager(this);
	}
	
	protected abstract void createParts();
	
	protected abstract void addBehaviours();
	
	public abstract void draw();
	
	protected void addBehaviour(Behaviour b) {
		behaviourManager.add(b);
	}
	
	public void setBody(Body _body) {
		// use prev body to set values of new body.
		Body prevBody = body;
		int prevColor = prevBody.color;
		
		if(_body.getCreature() == null)
			_body.setCreature(this);
		if(_body.getPos() == null)
			_body.pos = pos;
		if(_body.getWidth() == -1)
			_body.setWidth(prevBody.width);
		if(_body.getHeight() == -1)
			_body.setHeight(prevBody.height);
		
		// set new body.
		body = _body;
		// recreate PShapeBody, after all body fields are restored.
		// note, must be a cleaner way to do this.
		body.bodyPShape = body.createBody();
		
		// re-establish limbs to attach to new body.
		limbManager.getLimbs().clear();
		limbManager.createLimbs();
		
		// re-establish color on body.
		body.setColor(prevColor);
		
		prevBody = null;
	}
	public void setLimbManager(LimbManager _limbManager) {
		if(_limbManager.getCreature() == null)
			_limbManager.setCreature(this);
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
