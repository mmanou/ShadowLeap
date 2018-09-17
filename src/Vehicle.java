import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

abstract public class Vehicle extends Sprite {
	double velocity;

	public Vehicle(String imageSrc, float x, float y, boolean flipImg, float velocity) throws SlickException {
		super(imageSrc, x, y, flipImg);
		this.velocity = velocity;
	}

	@Override
	public void update(Input input, int delta) {
		addToX((float)(velocity * delta));
		
		/* Reset right-moving bus */
		if(this.getX() > App.SCREEN_WIDTH + (World.TILE_WIDTH / 2)) {
			setX(-World.TILE_WIDTH);
		}

		/* Reset left-moving bus */
		if (this.getX() < -World.TILE_WIDTH) {
			setX(App.SCREEN_WIDTH + (World.TILE_WIDTH / 2));
		}
		
		super.update(input, delta);
	}
}
