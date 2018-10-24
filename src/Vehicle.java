import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Moves by wrapping around to the opposite side of the screen when the edge is reached.
 */
abstract public class Vehicle extends Sprite {
	private double velocity;

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
	
	/**
	 * @return velocity
	 */
	public double getVelocity() {
		return this.velocity;
	}
	/**
	 * @param velocity Set new velocity of type double
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
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
