package behaviour;

import processing.core.PVector;
import creature.*;
import pbox2d.*;
import main.World;

import org.jbox2d.common.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;

public class PBox2DBehaviour extends Behaviour {
	// width and height can be referenced by c.
	// A static reference to our box2d world
	static public PBox2D box2d;
	private org.jbox2d.dynamics.Body body;

	static private int box2dFrameCount; // used to make sure box2d is stepped through only once per frame.

	/* Defines how box2d defines collision shape*/
	public enum CreatureShape {SQUARE, CIRCLE};

	private CreatureShape creatureShape;
	
	public PBox2DBehaviour(Creature _creature) {
		super(_creature);
		creatureShape = CreatureShape.SQUARE;
		// create box2D world if not created already (lazy instantiation)
		createBox2DWorld();
		addToBox2dWorld();
		box2dFrameCount = 1;
	}
	
	private void createBox2DWorld() {
		if(box2d == null) {
			box2d = new PBox2D(p);
			box2d.createWorld();
			// Turn on collision listening!
			box2d.listenForCollisions();
			box2d.setGravity(0, 0);
			addWorldBorders();
		}
	}
	
	private void addToUpdate(Updateable o) {
		// observer pattern. Used to register update() and draw() methods within the passed object.
		getUpdateables().add(o);
	}
	
	private void addToBox2dWorld() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(box2d.coordPixelsToWorld(creature.getPos()));
		body = box2d.createBody(bd);
		
		Shape shape;
		
		// Define the shape for collision detection
		switch (creatureShape) {
		case CIRCLE:
			shape = defineCircleShape();
			break;
		case SQUARE:
			shape = defineSquareShape();
			break;
		default:
			shape = defineSquareShape();
			break;
		}
		
		
	    FixtureDef fixtureDef = new FixtureDef();
	    // Parameters that affect physics
	    fixtureDef.density = 1f;
	    fixtureDef.friction = 0.3f;
	    fixtureDef.restitution = 0.82f; // bounce off other objects
		
	    // Attach shape to fixture
	    fixtureDef.shape = shape;
	    
	    // Attach fixture to body
	    body.createFixture(fixtureDef);
	    
	    body.resetMassData();
	    
	    body.setLinearVelocity(new Vec2(p.random(-4,4),p.random(-4,4)));
	    body.setAngularVelocity(p.random(-1,1));

	    // add application-specific body data. Box2d body now knows the creature it is attached to.
	    // can be retrieved by casting body.getUserData() to Creature.
	    body.setUserData(creature);
	}
	
	private Shape defineSquareShape() {
		// note: 2 seems rather arbitrary; can't really figure out why it cant just be full width/height.
	    float box2dW = box2d.scalarPixelsToWorld((int)(creature.getBody().getWidth()/2));
	    float box2dH = box2d.scalarPixelsToWorld((int)(creature.getBody().getHeight()/2));
	    PolygonShape polygonShape = new PolygonShape();
	    polygonShape.setAsBox(box2dW, box2dH);
	    return polygonShape;
	}
	private Shape defineCircleShape() {
		float box2dR = box2d.scalarPixelsToWorld(creature.getBody().getWidth()/2);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(box2dR);
		return circleShape;
	}

	@Override
	public void update() {
		// We must always step through time!
//		if(p.frameCount == box2dFrameCount) {
			box2dFrameCount++;
			box2d.step();
//		}
		move();
		
		updateUpdateables();
		
		// returns head of the world body list (as a body).
		// use Body.getNext() to get the next body in the world list.
		org.jbox2d.dynamics.Body b = box2d.world.getBodyList();
		while(b != null) {
			Vec2 pos = b.getPosition();
			// draw box at position
			p.pushStyle();
			p.fill(0);
			// transform from box2d world to pixel space
			PVector pixelSpace = box2d.coordWorldToPixelsPVector(pos);
			p.rectMode(p.CENTER);
			p.rect(pixelSpace.x, pixelSpace.y, 10, 10);
			p.popStyle();
			b = b.getNext();
		}

	}

	// Collision event functions!
	static public void beginContact(Contact c) {
	  // Get both shapes
	  Fixture fA = c.getFixtureA();
	  Fixture fB = c.getFixtureB();
	  // Get both bodies
	  org.jbox2d.dynamics.Body b1 = fA.getBody();
	  org.jbox2d.dynamics.Body b2 = fB.getBody();
	  // Get our objects that reference these bodies
	  Creature c1 = (Creature) b1.getUserData();
	  Creature c2 = (Creature) b2.getUserData();
	  
	  // if c1 and c2 are creatures, then run collisionAction
	  if(c1 instanceof Creature && c2 instanceof Creature) {
		  collisionAction(c1);
		  collisionAction(c2);
	  } else {
		  // else, it could be a collision between creature and boundary; do nothing for now.
	  }
	}
	
	
	static private void collisionAction(Creature c) {
		PBox2DBehaviour collisionBehaviourForCreature = (PBox2DBehaviour) c.getBehaviourManager().getBehaviours().get(PBox2DBehaviour.class);
		collisionBehaviourForCreature.collisionAction();
	}
	private void collisionAction() {
		creature.getBody().setFillColor(0xFFBB0000); // hexadecimal colour: 0x[alpha][red][green][blue]
	}
	
	static public void endContact(Contact c) {
		  // Get both shapes
		  Fixture fA = c.getFixtureA();
		  Fixture fB = c.getFixtureB();
		  // Get both bodies
		  org.jbox2d.dynamics.Body b1 = fA.getBody();
		  org.jbox2d.dynamics.Body b2 = fB.getBody();
		  // Get our objects that reference these bodies
		  Creature c1 = (Creature) b1.getUserData();
		  Creature c2 = (Creature) b2.getUserData();
		  
		  if(c1 instanceof Creature && c2 instanceof Creature) {
				c1.getBody().setFillColor(0xFF007744); // hexadecimal colour: 0x[alpha][red][green][blue]
				c2.getBody().setFillColor(0xFF007744); // hexadecimal colour: 0x[alpha][red][green][blue]
		  } else {
			  // else, it could be a collision between creature and boundary; do nothing for now.
		  }
	}
	
	
	@Override
	protected void move() {
		// here we place the position from the physics engine, back to the pos of the creature.
		Vec2 physicsPos = box2d.getBodyPixelCoord(this.body);
		float a = body.getAngle(); // already in radians it seems
		creature.setAngle(-a);
		
		creature.setPos(new PVector(physicsPos.x, physicsPos.y));
	}


	private void addWorldBorders() {
		// left boundary
		new Boundary(getScreenWidth()-5, getScreenHeight()/2, 10, World.getScreenHeight());
		// right boundary
		new Boundary(5, getScreenHeight()/2, 10, getScreenHeight());
		// top boundary
		new Boundary(getScreenWidth()/2, 5, getScreenWidth(), 10);
		// bottom boundary
		new Boundary(getScreenWidth()/2, getScreenHeight()-5, World.getScreenWidth(), 10);
	}
	
	class Boundary implements Updateable {
		float x, y, w, h;
		org.jbox2d.dynamics.Body b;
		
		Boundary(float _x, float _y, float _w, float _h) {
			x = _x;
			y = _y;
			w = _w;
			h = _h;
			
			// Create shape
			PolygonShape polyShape = new PolygonShape();
			float box2dW = box2d.scalarPixelsToWorld(w/2);
			float box2dH = box2d.scalarPixelsToWorld(h/2);
			polyShape.setAsBox(box2dW, box2dH);
			
			// Attach shape to fixture
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polyShape;
			
			fixtureDef.density = 0;    // No density means it won't move!
			fixtureDef.friction = 0.3f;

			// Define a body
			BodyDef bodyDef = new BodyDef();
			Vec2 posInBox2dWorld = box2d.coordPixelsToWorld(new PVector(x,y));
			bodyDef.position.set(posInBox2dWorld);
			// Create a body in box2d world using body definition
			org.jbox2d.dynamics.Body body = box2d.createBody(bodyDef);
			// Attach fixture to body
			body.createFixture(fixtureDef);
			
			addToUpdate(this);
		}
		
		public void update() {}
		
		public void draw() {
		    p.pushStyle();
		    p.fill(0);
		    p.stroke(0);
		    p.rectMode(p.CENTER);
		    p.rect(x,y,w,h);
		    p.popStyle();
		}
		
	}
	
	
}
