package creature.creeper;

import creature.worm.Worm;

public class Creeper extends Worm {
	
	public Creeper() {
		super();
	}

	@Override
	protected void createParts() {
		// create body
		body = new CreeperBody(this, pos, 15, 45);
	}
	
	
}
