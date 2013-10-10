package creature.virus;

import creature.*;

import processing.core.*;

public class Tentacle extends Limb {

	  float tOffset; // special offset to make this tentacle unique.

	  // AABB maybe? so instead of r for radius and length, we can use the width/height
	  // of the AABB?

	  public Tentacle(Creature _creature, PVector _pos, float _ang) {
		super(_creature, _pos);
	    angle = _ang;
	    height = 16; // COULD BE SELECTED FOR PARAMETER CONTROL.
	    width = 20; // width being radius of biggest part of tentacle.
	    
		tOffset = p.random(0, 2); // tentacle offset
	  }
	    
	  
	  public void draw() {
		  p.fill(60, 70, 70);
		  p.pushMatrix();
		  p.translate(pos.x, pos.y);

		  	float n = p.noise(noiseInc + tOffset);
			//////// COULD BE SELECTED FOR PARAMETER CONTROL.
			float tentacleTwist = p.map(n,0,1,3.6f,6.29f); // 3.6-6.29 chosen as nice range for tentacles to move.
			noiseInc += 0.006;
			p.rotate(getAngle() + tentacleTwist); 
		
			// re-map the twist for recursive tentacles to twist more at ends
			tentacleTwist = p.map(tentacleTwist, 3.6f, 6.29f, -2.1f, 2.1f);
			recursiveTentacles(getHeight(), tentacleTwist);
	    
		  p.popMatrix();
	  }
	  
	  // annoying field just for noise.
	  private float noiseInc = 0;
	  
	  private void recursiveTentacles(float l, float tentacleTwist) {
		  if(l > 0) {      
			  //////// COULD BE SELECTED FOR PARAMETER CONTROL.
			  p.rotate(p.radians(tentacleTwist));
			  //////// 2.5 COULD BE SELECTED FOR PARAMETER CONTROL.
			  p.ellipse(0,   (width-(width*(l/height))) * 2.5f,   width*(l/height),   width*(l/height));
//			  scaleX *= 3.5;
//			  scaleY *= 3.5;
//			  p.triangle(x-scaleX, y+scaleY, x, y-scaleY, x+scaleX, y+scaleY);

			  l--;
			  recursiveTentacles(l, tentacleTwist);
		  }
	  }
	  
	  
//	  @Override
	  public void update() {
	  }


	@Override
	public void setColor(int color) {
	}
	  
}
