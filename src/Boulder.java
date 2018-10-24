import org.newdawn.slick.SlickException;

/**
 * Solid tile that can be destroyed by Laserbolts
 */
public class Boulder extends Tree {

	public Boulder(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y);
		this.setSolid(true);
	}

	@Override
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Laserbolt) {
			this.setMarkedForDeath(true);
		}
	}
}
