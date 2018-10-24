import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Platform which submerges periodically.
 */
public class Turtle extends Vehicle {
	private static final int SURFACE_TIME = 7000;
	private static final int SUBMERGE_TIME = 2000;
	
	private boolean isSubmerged = false;
	private long timer = 0;

	public Turtle(String imageSrc, float x, float y, boolean flipImg,
			float width, float height, float velocity) throws SlickException {
		super(imageSrc, x, y, flipImg, width, height, velocity);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (this.isSubmerged() == false) {
			if (other instanceof Player) {
				other.addToX((float)(getVelocity() * delta));
			}
		}
	}
	
	@Override
	public void render() {
		if (this.isSubmerged() == false) {
			super.render();
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		timer += delta;
		if (isSubmerged == false) {
			if (timer >= SURFACE_TIME) {
				setSubmerged(true);
				timer = 0;
			}
		}
		else if (isSubmerged == true) {
			if (timer >= SUBMERGE_TIME) {
				setSubmerged(false);
				timer = 0;
			}
		}
		super.update(input, delta);
	}

	/**
	 * @return isSubmerged
	 */
	public boolean isSubmerged() {
		return isSubmerged;
	}
	
	/**
	 * @return Set new boolean value for isSubmerged.
	 */
	public void setSubmerged(boolean isSubmerged) {
		this.isSubmerged = isSubmerged;
	}
	
}
