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
	List<Creature> creatures = World.getPopulationDirector().creatures; // reference to list of all creatures in world.
	
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
		
		for(int i=0; i<creatures.size(); i++) {
			Creature c2 = creatures.get(i);
			if(creature == c2) continue; // if comparing same creature, continue with loop
			Map<Class<? extends Behaviour>, Behaviour> c2Behaviours = c2.getBehaviourManager().getBehaviours();
			
			// Only if c2 has a collisionBehaviour
			if(c2Behaviours.containsKey(CollisionBehaviour.class)) {
				// get the collisionBehaviour of c2
				CollisionBehaviour c2CollisionBehaviour = (CollisionBehaviour) c2Behaviours.get(CollisionBehaviour.class);
				
				// Now, check if bounding boxes collide
				if(AABB.testOverlap(aabbWorldSpace, c2CollisionBehaviour.aabbWorldSpace)) {
					boundingBoxCollided(); // c1 collided event
					c2CollisionBehaviour.boundingBoxCollided(); // c2 collided event
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
		p.rect(aabbWorldSpace.lowerBound.x, aabbWorldSpace.upperBound.y, width, height);
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
		// TODO Auto-generated method stub

	}


	@Override
	public void startMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void freeze() {
		// TODO Auto-generated method stub

	}

}
