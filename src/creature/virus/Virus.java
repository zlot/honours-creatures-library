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
	  
	  protected void createParts() {
		  int w = (int) p.random(70, 130);
		  body = new VirusBody(this, pos, w, w);
		  
		  limbManager = new TentacleManager(this); // w/2 is radius
	  }
	  
	  protected void addBehaviours() {
//		  addBehaviour(new PBox2DBehaviour(this));
//		  addBehaviour(new CollisionBehaviour(this));
	  }
	  
	}