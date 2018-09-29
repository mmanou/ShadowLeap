import org.newdawn.slick.SlickException;

public class Tree extends Sprite {

	public Tree(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false);
		this.setSolid(true);
	}
}
