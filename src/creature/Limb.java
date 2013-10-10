package creature;

import processing.core.PVector;

public abstract class Limb extends Part {
	protected float angle; // angle on body. ALREADY IN RADIANS.
	
	protected float width;
	protected float height;
	
	protected Limb(Creature _creature, PVector _pos) {
		super(_creature, _pos);
	}
	
	@Override
	public abstract void draw();
	
	public abstract void update();
	
	public void setAngle(float _angle) {
		angle = _angle;
	}
	
	public float getAngle() {
    	return angle;
    }
    public float getWidth() {
    	return width;
    }
    public float getHeight() {
    	return height;
    }

}
