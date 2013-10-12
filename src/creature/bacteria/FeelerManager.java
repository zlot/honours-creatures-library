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
	}

	@Override
	public void createLimbs() {
		// Create limbs around body
		// have to find perimeter of body. How to do this?
		// PSHAPE and vertices!
		float width = creature.getBody().getWidth();
		float height = creature.getBody().getHeight();
		
		float feelerWidth = height * 0.18f;
		
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
