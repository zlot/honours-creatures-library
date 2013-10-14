package creature.virus;

import behaviour.CollisionBehaviour;
import behaviour.PBox2DBehaviour;
import behaviour.MoveBehaviour;
import creature.*;
import creature.millipede.MillipedeBody;
import creature.squarething.SquareThingBody;
import processing.core.*;

public class Virus extends Creature {
	  
	  public Virus() {
		  super(); // creates behaviourManager
		  setPos(new PVector(p.random(getScreenWidth()), p.random(getScreenHeight())));
		  createParts();
		  addBehaviours();
	  }
	  
	  public void draw() {
		  // TODO:: incorporate this up a step into Creature.
		  p.pushMatrix();
			p.translate(getPos().x, getPos().y);
		  	p.rotate(-angle); // has to be -angle. Why? I don't know.
			body.draw();
			limbManager.draw();
		  p.popMatrix();
	  }
	  
	  protected void createParts() {
		  int w = (int) p.random(70, 130);
		  body = new VirusBody(this, pos, w, w);
		  
		  limbManager = new TentacleManager(this); // w/2 is radius
	  }
	  
	  protected void addBehaviours() {
		  addBehaviour(new PBox2DBehaviour(this));
	  }
	  
	}