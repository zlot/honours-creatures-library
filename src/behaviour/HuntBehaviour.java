package behaviour;

import java.util.List;
import java.util.Map;

import main.World;
import creature.AABB;
import creature.Behaviour;
import creature.Creature;
import exception.BoundingBoxNotPresentException;
import processing.core.PVector;

/**
 * Must be used in conjunction with CollisionBehaviour
 * @author zlot
 *
 */
public class HuntBehaviour extends Behaviour {

	
	float nOffset; // special offset to make virus movement unique.
	// annoying field just for noise
	float noiseInc = 0;
	float mmStep = 0.5f; // movement step
	List<Creature> creatures = World.getPopulationDirector().creatures; // reference to list of all creatures in world.

	float range; // scalar value based on bounding box multiplication, how far from creature before it hunts.

	public HuntBehaviour(Creature _creature) {
		super(_creature);
		nOffset = p.random(-100, 100);
		range = 2;
	}
	public HuntBehaviour(Creature _creature, float _range) {
		super(_creature);
		nOffset = p.random(-100, 100);
		range = _range;
	}
	
	@Override
	public void update() {
		// if collision behaviour present for this creature, use its bounding box.
		// else, throw exception BoundingBoxNotPresentException
		// TODO:: this could be simplified with eg:
		//        ... .getBehaviours().hasBehaviour("CollisionBehaviour"); // method takes string, finds class, runs code below
//		try {
//			checkForCollisionBehaviour();
//		} catch(BoundingBoxNotPresentException ex) {
//			ex.printStackTrace();
//			return;
//		}
		
		// CollisionBehaviour is present.
		// get boundingbox of this creature, scale it up by range factor
		//c.getBehaviourManager().getBehaviours().
		
		AABB tempAABB = creature.getBody().getAABB();
		// extend AABB to huntingAABB
		// lowerVertex * range, upperVertex * range
		
		AABB huntingAABB = new AABB(PVector.mult(tempAABB.lowerBound, range), PVector.mult(tempAABB.upperBound, range));
		
		// check positions of all other creatures
		for(Creature c2 : creatures) {
			// huntingAABB vs c2 bounding box
			
			//TODO:: STUCK HERE. because I need AABB always in world space again.
			// I guess AABB should ALWAYS be in world space anyway. This means that AABB for body part gets updated
			// every frame, using update() in Body.
			// in this case, fuck, just switch now to PBox2D so this shit can actually work with PBox2D.
			// its kind of useless otherwise. So switch, we need physics!!
			
//			if(AABB.testOverlap(huntingAABB, c2CollisionBehaviour.aabbWorldSpace)) {
//				boundingBoxCollided(); // c1 collided event
//				c2CollisionBehaviour.boundingBoxCollided(); // c2 collided event
//			}
			
		}
		// if there is a creature within radius, move towards it
		
		move();
		

	}

//	private boolean checkForCollisionBehaviour() {
//		//if(boundingBox != null)
//		if(c.getBehaviourManager().getBehaviours().containsKey(CollisionBehaviour.class)) {
//			return true;
//		} else {
//			throw new BoundingBoxNotPresentException("No bounding box! HuntBehaviour requires the creature to also have a CollisionBehaviour.");
//		}
//	}
	
	
	@Override
	protected void move() {
		// else, random walk.
	    PVector pos = creature.getBody().getPos();
		
		PVector vel = new PVector();
	    float n = p.noise(noiseInc + nOffset);
	    float nMapped = p.map(n, 0, 1, -mmStep, mmStep); // for x
	    vel.x = nMapped;
	    
	    n = p.noise(noiseInc + 3); // 3 is arbitrary to have it different to y
	    nMapped = p.map(n, 0, 1, -mmStep, mmStep); // for y
	    vel.y = nMapped;
	    
	    noiseInc += 0.006;

	    pos.add(vel);
	    pos.x = p.constrain(pos.x, 0, getScreenWidth());
	    pos.y = p.constrain(pos.y, 0, getScreenHeight());
	}


}
