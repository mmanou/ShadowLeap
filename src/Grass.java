import org.newdawn.slick.SlickException;

/**
 * Image tile with no effect on other objects in the game.
 */
public class Grass extends Sprite {

	public Grass(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false);
	}
}
