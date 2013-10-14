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
//	    int numTentacles = (p.random(1) < 0.5) ? 6 : 7;
//	    float rndOffset = p.random(360); // for further uniqueness per virus.
	    
		ArrayList<PVector> bodyVertices = creature.getBody().getVertices();
		
		for(int i=0; i<bodyVertices.size(); i+=1) { // could be random how many every nth vertice! (arbitrary).
			// get vertex.
			PVector v = bodyVertices.get(i);
			float angleInRadians = v.heading();
			
			Tentacle t = new Tentacle(creature, new PVector(v.x, v.y), angleInRadians);
	      
	    	// add to tentacles
	    	limbs.add(t);
		}
	    
	}

	float tOffset; // special offset to make this tentacle unique.

}
