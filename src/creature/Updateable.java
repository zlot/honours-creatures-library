package creature;

/**
 * Interface used to include other classes to be updated. 
 * Currently used only in Behaviours. Currently used only for the PBox2DBehaviour.
 *
 */
public interface Updateable {

	public void update();
	
	public void draw();
	
}