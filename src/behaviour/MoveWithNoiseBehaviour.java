package behaviour;

import creature.Behaviour;
import creature.Creature;
import processing.core.*;
import main.World;

public class MoveWithNoiseBehaviour extends Behaviour {

	float noiseInc;
	
	public MoveWithNoiseBehaviour(Creature _creature) {
		super(_creature);
		noiseInc = p.random(0, 200); // give this creature its own unique movement from the others of its species.
	}
	
	@Override
	public void update() {
		float noiseVal = p.noise(noiseInc);
		
		float x = PApplet.map(noiseVal, 0, 1, 0, World.getScreenWidth());
		
		noiseVal = p.noise(noiseInc + 100); // big offset so y is different. 
		float y = PApplet.map(noiseVal, 0, 1, 0, World.getScreenHeight());
		
		noiseInc += 0.007;
		
		PVector desiredTarget = new PVector(x, y);
		
		creature.moveToTarget(desiredTarget, 15, 0.07f);
	}
	
	@Override
	public void move() {
		
	}
	
}
