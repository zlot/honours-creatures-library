package creature.bacteria;

import java.util.ArrayList;

import behaviour.MoveBehaviourWithAng;
import processing.core.PShape;
import processing.core.PVector;
import toxi.math.waves.*;
import creature.Creature;
import creature.Limb;
import creature.LimbManager;

public class FeelerManager extends LimbManager {

	AbstractWave sineWave;
	float sineAngle;
	
	public FeelerManager(Creature _c) {
		super(_c);
		createLimbs();
		
		sineWave = new SineWave(0, p.radians(4), 1, p.random(5));
	}
	
	@Override
	public void update() {
		sineAngle = sineWave.update();
		updateDynamicPosition();
	}

	
	/* note: might slow down sketch! */
	protected void updateDynamicPosition() {
		// follow body dynamically?
		PShape bodyPShape = creature.getBody().getBodyPShape();
		int vertexCount = bodyPShape.getVertexCount();
		
		for(int i=0; i<vertexCount; i++) {
			// has vertex count changed (ie has the body of creature been replaced)?
			// if so, break out.
			if(limbs.size() != vertexCount)
				break;
			
			// for each, set corresponding feeler to new position.
			limbs.get(i).getPos().x = bodyPShape.getVertexX(i);
			limbs.get(i).getPos().y = bodyPShape.getVertexY(i);
		}
	}
	
	
	@Override
	public void createLimbs() {
		float width = creature.getBody().getWidth();
		float height = creature.getBody().getHeight();
		
		float feelerWidth = width * 0.18f;
		
		for(int i=0; i<creature.getBody().getBodyPShape().getVertexCount(); i++) {
			// get vertex.
			PVector v = new PVector(creature.getBody().getBodyPShape().getVertexX(i),
					creature.getBody().getBodyPShape().getVertexY(i));
			// use PVector as location of limb.
			// angle. Um. 
			float angleInRadians = v.heading();
			
			Limb feeler = new Feeler(creature, new PVector(v.x, v.y), feelerWidth, angleInRadians);
			
			// TODO: have to find a way to make this a FORCED thing.
			limbs.add(feeler);
		}
		
	}

	
	public void createLimbsWORKINGBUTOLD() {
		float width = creature.getBody().getWidth();
		float height = creature.getBody().getHeight();
		
		float feelerWidth = width * 0.18f;
		
		// note: this gets the static vertices. If they update during draw loop, these vertices are not updated.
		ArrayList<PVector> bodyVertices = creature.getBody().getVertices();
		
		for(int i=0; i<bodyVertices.size(); i++) {
			// get vertex.
			PVector v = bodyVertices.get(i);
			// use PVector as location of limb.
			// angle. Um. 
			float angleInRadians = v.heading();
			
			Limb feeler = new Feeler(creature, new PVector(v.x, v.y), feelerWidth, angleInRadians);
			
			// TODO: have to find a way to make this a FORCED thing.
			limbs.add(feeler);
		}
		
	}

}
