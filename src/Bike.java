import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Bike extends Traffic {

	public Bike(String imageSrc, float x, float y, boolean flipImg, float velocity) throws SlickException {
		super(imageSrc, x, y, flipImg, velocity);
	}

	@Override
	public void update(Input input, int delta) {
		if ((velocity > 0 && isImageFlipped() == true)
				|| (velocity < 0 && isImageFlipped() == false)) {
			flipImage();
		}
		
		if ((getX() >= 1000 && this.velocity > 0) || (getX() <= 24 && this.velocity < 0)) {
			this.velocity = -this.velocity;
		}
		
		super.update(input, delta);
	}
}
