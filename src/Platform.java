import org.newdawn.slick.SlickException;

/**
 * Moves the Players and protect them from Water tiles when they are in collision.
 */
public class Platform extends Vehicle {

	public Platform(String imageSrc, float x, float y, boolean flipImg, 
			float width, float height, float velocity) throws SlickException {
			super(imageSrc, x, y, flipImg, width, height, velocity);
	}
	
	public Platform(String imageSrc, float x, float y, boolean flipImg, 
			float velocity) throws SlickException {
			super(imageSrc, x, y, flipImg, velocity);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Player) {
			other.addToX((float)(getVelocity() * delta));
		}
		if (other instanceof ExtraLife) {
			((ExtraLife) other).setVelocity(this.getVelocity());
		}
	}
	
	
}
