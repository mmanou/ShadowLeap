import org.newdawn.slick.SlickException;

/**
 * Solid tile that cannot be interacted with. Also dictates the location of FrogHoles.
 */
public class Tree extends Sprite {

	public Tree(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false);
		this.setSolid(true);
	}
}
