import org.newdawn.slick.SlickException;

/**
 * Solid vehicle. Pushes the player in its direction of travel.
 */
public class Bulldozer extends Vehicle {

	public Bulldozer(String imageSrc, float x, float y, boolean flipImg, float velocity)
			throws SlickException {
		super(imageSrc, x, y, flipImg, velocity);
		this.setSolid(true);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Player) {
			other.addToX((float)(getVelocity() * delta));
		}
	}
}
