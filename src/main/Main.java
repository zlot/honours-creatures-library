package main;
import java.util.List;

import loader.PClass;




import processing.core.*;

public class Main extends PApplet {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
//		PApplet.main(new String[] { "--present", "Main" }); // DEPRECATED. Should update this in Processing website tutorial github!
		PApplet.main(new String[] { "--location=30,30", "main.Main" });
		
//		initGUIElements();
	}

	public PApplet p = this;
	
	World world;
	
//	public List<SuperCreature> masterObjectList;
	

	private static void initGUIElements() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		final JComboBox_Example jComboBox_Example = new JComboBox_Example();
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	jComboBox_Example.createAndShowGUI();
            }
        });
	}
	
	
	
	@Override
	public void setup() {
		int width = 1050;
		int height = 900;
		colorMode(HSB,360,100,100);
		size(1050,900, P2D);
		world = new World(this, width, height);

	}
	@Override
	public void draw() {
	  world.run();
  }
	
	public void mouseClicked() {
	}
	public void mouseDragged() {
	}
	
	public void keyPressed() {
		switch (key) {
		case 's':
			break;
		case 'd':
			break;
		}
	}
}