package creature.creeper;

import processing.core.PShape;
import processing.core.PVector;
import creature.Body;
import creature.Creature;
import creature.worm.WormBody;

public class CreeperBody extends WormBody {
	
	public CreeperBody(Creature _creature, PVector _pos, float _width, float _height) {
		super(_creature, _pos, _width, _height);
	}

	@Override
	protected void wiggle(int deg) {
		for(int i=0; i<bodyPShape.getVertexCount(); i++) {
			// take the sine update, ++ a float value as it travels down the line
			PVector vertex = bodyPShape.getVertex(i);
			vertex.y += p.sin(p.radians((deg + i) * 6)) * 0.5;
			bodyPShape.setVertex(i, vertex.x, vertex.y);
		}
	}
	
	
	@Override
	protected PShape createBody() {
		PShape wormBody = p.createShape();
		wormBody.beginShape();
		wormBody.noFill();
		wormBody.strokeWeight(width);
	    for(int i=(int)-width/2; i<width/2; i+=2) { // put to 1 for even more wormy goodness!
	    	wormBody.vertex(i, 0);
	    }
	    wormBody.endShape();
	    return wormBody;
	}
}
