package loader;
import processing.core.PApplet;


/**
 * Used as a helper superclass such that the PApplet does not
 * have to be passed as an argument into every constructed class.
 * 
 * @author zlot
 */
public abstract class PClass {

	protected static PApplet p;
	private static int width;
	private static int height;
	private static int buffer; // buffer for edges of screen.
	
	public static boolean insertPApplet(PApplet _p) {
		if(p == null)
			p = _p;
		return true;
	}
	public static void setWidthAndHeight(int w, int h, int _screenEdgeBuffer) {
		width = w;
		height = h;
		buffer = _screenEdgeBuffer;
	}
	
	public static int getScreenWidth() {
		return width;
	}
	public static int getScreenHeight() {
		return height;
	}
	public static float getScreenWidthWithBuffer() {
		return width + buffer;
	}
	public static float getScreenHeightWithBuffer() {
		return height + buffer;
	}
	public static float getBuffer() {
		return buffer;
	}
}
