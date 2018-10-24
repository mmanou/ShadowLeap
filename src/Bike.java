import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Obstacle, moves by reversing direction at the edge of the screen
 */
public class Bike extends Traffic {

	public Bike(String imageSrc, float x, float y, boolean flipImg, float velocity) throws SlickException {
		super(imageSrc, x, y, flipImg, velocity);
	}

	@Override
	public void update(Input input, int delta) {
		if ((getVelocity() > 0 && isImageFlipped() == true)
				|| (getVelocity() < 0 && isImageFlipped() == false)) {
			flipImage();
		}
		
		if ((getX() >= 1000 && this.getVelocity() > 0) || (getX() <= 24 && this.getVelocity() < 0)) {
			this.setVelocity(-this.getVelocity());
		}
		
		super.update(input, delta);
	}
}
