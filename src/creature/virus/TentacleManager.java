package creature.virus;

import java.util.ArrayList;

import processing.core.PVector;
import creature.*;
import creature.bacteria.Feeler;

public class TentacleManager extends LimbManager {

	
	public TentacleManager(Creature _c) {
		super(_c);
		createLimbs();
	}

	
	public void createLimbs() {
	    float r = creature.getBody().getWidth()/2; // radius is half of body
	    
	///// COULD BE SELECTED FOR PARAMETER CONTROL
	    int numTentacles = (p.random(1) < 0.5) ? 6 : 7;
	    
	    float rndOffset = p.random(360); // for further uniqueness per virus.
	    
	    
		ArrayList<PVector> bodyVertices = creature.getBody().getVertices();
		
		for(int i=0; i<bodyVertices.size(); i+=8) { // every 8th vertice! (arbitrary).
			// get vertex.
			PVector v = bodyVertices.get(i);
			float angleInRadians = v.heading();
			
	    	//float ang = p.radians(i*(360/numTentacles) + rndOffset);
			
////////////////////// IM HERE:.
			// tPos is tentacle Position.
	    	//tPos = new PVector(r * p.cos(ang), r * p.sin(ang));
			
			// ang is the angle radiating out from pos centre. Can get via heading, maybe? or atan.
	    	//Tentacle t = new Tentacle(creature, tPos, ang);
			Tentacle t = new Tentacle(creature, new PVector(v.x, v.y), angleInRadians);
	      
	    	// add to tentacles
	    	limbs.add(t);
		}
	    
	}

	float tOffset; // special offset to make this tentacle unique.

}
