package behaviour;

import main.World;
import processing.core.PApplet;
import processing.core.PVector;
import creature.Behaviour;
import creature.Creature;


// The Nature of Code
// Daniel Shiffman
// http://natureofcode.com
public class FlowFieldBehaviour extends Behaviour {

	
	/** SINGLETON FLOWFIELD, for all creatures implementing flowfield! */
	private static FlowField flowFieldSingleton = new FlowField();
	
	public static FlowField getFlowField() {
		return flowFieldSingleton;
	}
	
	public FlowFieldBehaviour(Creature _creature) {
		super(_creature);
	}
	
	
	/* inner static class FlowField. Only one that is accessed by all creatures using a FlowFieldBehaviour */
	static class FlowField {
		
		PVector[][] field;
		int cols, rows; // Columns and rows
		int resolution; // How large is each "cell of the flow field
		
		public FlowField() {
			resolution = 20;
			// Determine the number of columns and rows based on sketch's width and height
			cols = World.getScreenWidth()/resolution;
			rows = World.getScreenHeight()/resolution;
			field = new PVector[cols][rows];
			init();
		}
		
		private void init() {
			// Reseed noise so we get a new flow field every time
			p.noiseSeed((int)p.random(10000));
			float xoff = 0;
			for (int i = 0; i < cols; i++) {
				float yoff = 0;
				for (int j = 0; j < rows; j++) {
					float theta = p.map(p.noise(xoff,yoff),0,1,0,PApplet.TWO_PI);
					// Polar to cartesian coordinate transformation to get x and y components of the vector
					field[i][j] = new PVector(PApplet.cos(theta),PApplet.sin(theta));
					yoff += 0.1;
				}
				xoff += 0.1;
			}
		}
		
		protected void display() {
			for(int i=0; i<cols; i++) {
				for(int j=0; j<rows; j++) {
					drawVector(field[i][j], i*resolution, j*resolution, resolution-2);
				}
			}
		}
		// Renders a vector object 'v' as an arrow and a location 'x,y'
		void drawVector(PVector v, float x, float y, float scalar) {
			p.pushMatrix();
			// Translate to location to render vector
			p.translate(x,y);
			p.stroke(0,100);
			// Call vector heading function to get direction (note that pointing up is a heading of 0) and rotate
			p.rotate(v.heading());
			// Calculate length of vector & scale it to be bigger or smaller if necessary
			float len = v.mag()*scalar;
			// Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
			p.pushStyle();
			p.stroke(255);
			p.strokeWeight(2);
			p.line(0,0,len,0);
			p.popStyle();
			p.popMatrix();
		}
		
		PVector lookup(PVector lookup) {
			int column = (int) (PApplet.constrain(lookup.x/resolution, 0, cols-1));
			int row = (int) PApplet.constrain(lookup.y/resolution, 0, rows-1);
			return field[column][row].get();
		}
	}

	
	@Override
	public void update() {
		// Implementing Reynolds' flow field following algorithm
		// http://www.red3d.com/cwr/steer/FlowFollow.html
	    // What is the vector at that spot in the flow field?
	    PVector desired = getFlowField().lookup(creature.getPos());
	    
//	    creature.steerToTarget(desired);
	    creature.moveToTarget(desired, 9, 0.09f);
    }
	
	
	@Override
	protected void move() {
	}

}
