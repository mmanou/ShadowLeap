import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

abstract public class Vehicle extends Sprite {
	double velocity;

	public Vehicle(String imageSrc, float x, float y, boolean flipImg, float velocity)
			throws SlickException {
		super(imageSrc, x, y, flipImg);
		this.velocity = velocity;
	}
	public Vehicle(String imageSrc, float x, float y, boolean flipImg,
			float width, float height, float velocity) throws SlickException {
		super(imageSrc, x, y, flipImg, width, height);
		this.velocity = velocity;
	}
	
	public double getVelocity() {
		return this.velocity;
	}

	@Override
	public void update(Input input, int delta) {
		addToX((float)(velocity * delta));
		
		/* Reset right-moving vehicle */
		if(this.getX() > App.SCREEN_WIDTH + (this.getWidth() / 2)) {
			setX(-this.getWidth());
		}

		/* Reset left-moving vehicle */
		if (this.getX() < -this.getWidth()) {
			setX(App.SCREEN_WIDTH + (this.getWidth() / 2));
		}
		
		super.update(input, delta);
	}
}
