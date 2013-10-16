package behaviour;

import java.util.List;
import java.util.Map;

import creature.AABB;
import creature.Behaviour;
import creature.Creature;
import main.World;
import processing.core.PVector;

public class CollisionBehaviour extends Behaviour {
	
	AABB aabb; // ref to creatue body's aabb.
	AABB aabbWorldSpace; // aabb translated to world space.
	List<Creature> creatures = World.getPopulationDirector().getCreatures(); // reference to list of all creatures in world.
	
	PVector pos;
	
	public CollisionBehaviour(Creature _creature) {
		super(_creature);
		pos = creature.getBody().getPos();
		aabb = creature.getBody().getAABB();
		// setup aabbWorldSpace
		aabbWorldSpace = aabb.clone();
	}

	@Override
	public void update() {
		PVector worldaabb = PVector.add(aabb.lowerBound, pos);
		
		aabbWorldSpace.lowerBound.x = worldaabb.x;
		aabbWorldSpace.lowerBound.y = worldaabb.y;
		
		worldaabb = PVector.add(aabb.upperBound, pos);
		
		aabbWorldSpace.upperBound.x = worldaabb.x;
		aabbWorldSpace.upperBound.y = worldaabb.y;
		
//		drawBoundingBox();
	
		/* loop creature with all other creatures and check for collision. */
		/* note: pretty inefficient. O(n^2). */
		for(Creature creatureToCompare : creatures) {
			if(creature == creatureToCompare) continue; // if comparing same creature, continue with loop
			// check if creature has this behaviour
			if(creatureToCompare.hasBehaviour(this.getClass())) {
				// if so, do collisionbehaviour check.
				// get the collisionBehaviour of c2
				CollisionBehaviour creatureToCompareCollisionBehaviour = (CollisionBehaviour) creatureToCompare.getBehaviourManager().getBehaviours().get(CollisionBehaviour.class);
				
				// Now, check if bounding boxes collide
				if(AABB.testOverlap(aabbWorldSpace, creatureToCompareCollisionBehaviour.aabbWorldSpace)) {
					boundingBoxCollided(); // c1 collided event
					creatureToCompareCollisionBehaviour.boundingBoxCollided(); // c2 collided event
				}
				
			}
			
		}
	}

	
	/**
	 * Testing purposes only. Make visible the bounding box.
	 */
	private void drawBoundingBox() {
		float width = creature.getBody().getWidth();
		float height = creature.getBody().getHeight();
		
		p.pushStyle();
		p.stroke(0, 100, 100);
		p.noFill();
		float centreX = aabbWorldSpace.lowerBound.x + (aabbWorldSpace.upperBound.x - aabbWorldSpace.lowerBound.x)/2;
		float centreY = aabbWorldSpace.lowerBound.y + (aabbWorldSpace.upperBound.y - aabbWorldSpace.lowerBound.y)/2;
		p.rect(centreX, centreY, width, height);
		p.popStyle();
	}
	
	
	/**
	 * Collided bounding boxes event
	 */
	private void boundingBoxCollided() {
		float width = creature.getBody().getWidth();
		float height = creature.getBody().getHeight();
		
		p.pushStyle();
		p.fill(0, 100, 100);
		p.rect(aabbWorldSpace.lowerBound.x, aabbWorldSpace.upperBound.y, width, height);
		p.popStyle();
	}
	
	
	@Override
	protected void move() {
	}


}
