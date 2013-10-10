package behaviour;

import creature.Behaviour;
import creature.Creature;
import processing.core.*;
import loader.PClass;
import main.World;

@SuppressWarnings("static-access")
public class MoveBehaviourWithAng extends Behaviour {

	float nOffset; // special offset to make virus movement unique.
	
	PVector acc, vel;
	private float ang; // angle that creature is moving.
	
	public MoveBehaviourWithAng(Creature  _creature) {
		super(_creature);
		nOffset = p.random(-100, 100);
		acc = new PVector();
		vel = new PVector();
	}
	
	// annoying field just for noise
	float noiseInc = 0;
	float mmStep = 0.015f; // movement step
	
	public float ang2;
	
	// noise-walk the pos
	public void move() {
	    PVector pos = creature.getBody().getPos();
		
	    float n = p.noise(noiseInc + nOffset);
	    float nMapped = p.map(n, 0, 1, -mmStep, mmStep); // for x
	    acc.x = nMapped;
	    
	    n = p.noise(noiseInc + nOffset + 13); // 13 is arbitrary to have it different to y
	    nMapped = p.map(n, 0, 1, -mmStep, mmStep); // for y
	    acc.y = nMapped;
	    
	    noiseInc += 0.002;

	    
	    // get angle of pos to vel
	    p.pushMatrix();
	    	p.translate(pos.x, pos.y);
	    	ang = p.atan2(vel.y, vel.x); // in radians
	    	// get angle of pos to acc
	    	ang2 = p.atan2(acc.y, acc.x); // in radians
	    p.popMatrix();
	    
	    
	    // set creature angle
	    creature.setAngle(ang);
	    
	    vel.add(acc);
	    pos.add(vel);

	    if(pos.x > World.getScreenWidthWithBuffer()) {
	      pos.x = 0;
	    } else if (pos.x < -World.getBuffer()) {
	      pos.x = World.getScreenWidthWithBuffer();
	    }
	    
	    if(pos.y > World.getScreenHeightWithBuffer()) {
	      pos.y = 0;
	    } else if (pos.y < -World.getBuffer()) {
	      pos.y = World.getScreenHeightWithBuffer();
	    }
	    
//	    p.println("acc.x: " + acc.x + " acc.y: " + acc.y);
//	    p.println("vel.x: " + vel.x + " vel.y: " + vel.y);
//	    p.println("pos.x: " + pos.x + " pos.y: " + pos.y);
    }

	@Override
	public void update() {
		move();
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
