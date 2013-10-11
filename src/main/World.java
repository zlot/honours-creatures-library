package main;
import processing.core.PApplet;
import creature.*;
import creature.virus.*;
import creature.worm.Worm;
import creature.bacteria.Bacteria;
import creature.millipede.Millipede;
import creature.squarething.*;
import loader.PClass;


public class World extends PClass {

	static int width;
	static int height;
	static int bgColor;
	
	private static PopulationDirector populationDirector;
	
	
	public World() {
		this(p, 1050, 900);
	}
	

	public World(PApplet _pApplet, int _width, int _height) {
		PClass.insertPApplet(_pApplet);
		width = _width;
		height = _height;
	    PClass.setWidthAndHeight(width, height, 150); // last arg is screen edge buffer value in pixels
		bgColor = p.color(12); // (240, 40, 40);
		
		addCreaturesToPopulation();
	}
	
	private void addCreaturesToPopulation() {
		
		populationDirector = PopulationDirector.getInstance();
		
		// need a creature factory maybe?
		// because we need to create each creature AND MAKE SURE a behaviour is attached to its behaviourManager.
		populationDirector.addCreatures(Virus.class, 1);
		
//	 	populationDirector.addCreatures(SquareThing.class, 25);
		
	 	populationDirector.addCreatures(Millipede.class, 4);
	 	
	 	populationDirector.addCreatures(Bacteria.class, 1);
//	 	populationDirector.addCreatures(Worm.class, 16);
	 	
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
		populationDirector.update();
		
	}

}
