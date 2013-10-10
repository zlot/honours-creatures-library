package creature.worm;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;

public class WormBody extends Body {

	PShape wormBody;
	int sineDegree; // degrees for wiggle.
	
	public WormBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
		setColor(0xff008888);
		
		wormBody = p.createShape();
		wormBody.beginShape();
		wormBody.stroke(color); // TODO: there should be a getter for color.
		wormBody.strokeWeight(width);
	    for(int i=0; i<height; i+=2) { // put to 1 for even more wormy goodness!
	    	wormBody.vertex(0, i);
	    }
	    wormBody.endShape();
		
	}

	@Override
	public void draw() {
	    p.pushMatrix();
	      sineDegree++;
	      if(sineDegree % 360 == 0 && sineDegree != 0) sineDegree = 0;
	      wiggle(sineDegree);
	      p.shape(wormBody); // draws millipede body
	    p.popMatrix();
		
	}
	
	private void wiggle(int deg) {
		for (int i = 0; i < wormBody.getVertexCount(); i++) {
			// take the sine update, ++ a float value as it travels down the line
			PVector v = wormBody.getVertex(i);
			v.x += p.sin(p.radians((deg + i) * 4)) * 0.5;
			//pos.add(new PVector(sineUpdate, 0));
			wormBody.setVertex(i, v.x, v.y);
		}
	}
	
	public int getWaveDegree() {
		return sineDegree;
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	protected PShape createBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
