package main;
import processing.core.PApplet;
import creature.*;
import creature.virus.*;
import creature.worm.Worm;
import creature.bacteria.Bacteria;
import creature.creeper.Creeper;
import creature.millipede.Millipede;
import creature.squarething.*;
import creature.trianglething.TriangleThing;
import loader.PClass;


public class World extends PClass {

	private int width, height; // copied values from static PClass width/height values.
	private int bgColor;
	
	private static PopulationDirector populationDirector;
	
	public World() {
		this(p, 1050, 900);
	}
	

	public World(PApplet _pApplet, int _width, int _height) {
		PClass.insertPApplet(_pApplet);
		width = _width; 
		height = _height; 
	    PClass.setWidthAndHeight(width, height, 80); // last arg is screen edge buffer value in pixels
	    bgColor = p.color(12); // (240, 40, 40);
		
		addCreaturesToPopulation();
	}
	
	private void addCreaturesToPopulation() {
		
		populationDirector = PopulationDirector.getInstance();
		
		// need a creature factory maybe?
		// because we need to create each creature AND MAKE SURE a behaviour is attached to its behaviourManager.
		populationDirector.addCreatures(Virus.class, 2);
		
	 	populationDirector.addCreatures(SquareThing.class, 12);
		
	 	populationDirector.addCreatures(Millipede.class, 2);
	 	
	 	populationDirector.addCreatures(TriangleThing.class, 3);
	 	populationDirector.addCreatures(Bacteria.class, 1);
	 	populationDirector.addCreatures(Worm.class, 2);
	 	populationDirector.addCreatures(Creeper.class, 2);
//		try {
//			populationDirector.addCreatures((Class<? extends Creature>)Class.forName("worldofcreatures$Car"), 7);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	 	
	}
	
	public static PopulationDirector getPopulationDirector() {
		return populationDirector;
	}

	public void setBgColor(int c) {
		bgColor = c;
	}
	
	
	public void run() {
		// refresh background
		p.fill(bgColor);
		p.rect(width/2,height/2,width,height);
		populationDirector.internalUpdate();
		
	}
	
	/**
	 * Convenience function. This is the exact same value as PClass.getScreenWidth().
	 * @return world/screen width.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Convenience function. This is the exact same value as PClass.getScreenWidth().
	 * @return world/screen height.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Convenience function. This calls PClass.getScreenWidthWithBuffer().
	 * @return width of world + screen buffer value: see getWorldBufferValue().
	 */
	public float getWidthWithBuffer() {
		return getScreenWidthWithBuffer();
	}
	/**
	 * Convenience function. This calls PClass.getScreenHeightWithBuffer().
	 * @return height of world + screen buffer value: see getWorldBufferValue().
	 */
	public float getHeightWithBuffer() {
		return getScreenHeightWithBuffer();
	}
	/**
	 * Convenience function. This calls PClass.getBuffer().
	 * @return screen buffer value: a uniform buffer value that can
	 * 		   be used utilised for events at the edges of the screen.
	 */
	public float getWorldBufferValue() {
		return getBuffer();
	}
}
