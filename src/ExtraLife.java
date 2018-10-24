import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Spawns and moves around on platforms. Can be collected by a Player to grant an additional life.
 */
public class ExtraLife extends Sprite {
	private static final int MOVE_INTERVAL = 2000;
	
	private long timer = 0; // amount of time this object has existed for
	private long moveTimer = 0; // amount of time since this object last moved on its platform
	private double velocity;
	private float platformWidth;
	private float XOnPlatform;
	private boolean movingRight = true;
	private boolean collected = false;
	private boolean onPlatform = false;

	/**
	 * @param imageSrc The path of the image to be displayed to represent this sprite in the game world.
	 * @param x The x-coordinate where this sprite spawns.
	 * @param y The y-coordinate where this sprite spawns.
	 * @param platformWidth Width of the Platform on which this ExtraLife spawns
	 * @throws SlickException
	 */
	public ExtraLife(String imageSrc, float x, float y, float platformWidth) throws SlickException {
		super(imageSrc, x, y, false);
		this.platformWidth = platformWidth;
		this.XOnPlatform = platformWidth / 2;
	}

	public long getTimer() {
		return timer;
	}
	
	@Override
	public void update(Input input, int delta) {
		timer += delta;
		moveTimer += delta;
		
		addToX((float)(velocity * delta));
		
		if (moveTimer >= MOVE_INTERVAL) {
			if (onPlatform == true) {
			if (movingRight == true) {
				if (XOnPlatform < platformWidth - World.TILE_WIDTH) {
					moveRight(World.TILE_WIDTH);
				}
				else {
					movingRight = false;
					moveLeft(World.TILE_WIDTH);
				}
			}
			else if (movingRight == false) {
				if (XOnPlatform > World.TILE_WIDTH) {
					moveLeft(World.TILE_WIDTH);
				}
				else {
					movingRight = true;
					moveRight(World.TILE_WIDTH);
				}
			}
			moveTimer = 0;
			}
		}
		
		/* Reset right-moving object */
		if(this.getX() > App.SCREEN_WIDTH + (this.platformWidth / 2)) {
			setX(-this.platformWidth);
		}

		/* Reset left-moving object */
		if (this.getX() < -this.platformWidth) {
			setX(App.SCREEN_WIDTH + (this.platformWidth / 2));
		}
		
		this.setOnPlatform(false);
		
		super.update(input, delta);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Player) {
			setCollected(true);
		}
		else if (other instanceof Platform) {
			setOnPlatform(true);
		}
	}

	private void setOnPlatform(boolean b) {
		onPlatform = b;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}
	
	public void moveRight(float x) {
		addToX(x);
		XOnPlatform += x;
	}
	
	public void moveLeft(float x) {
		addToX(-x);
		XOnPlatform -= x;
	}
	
	
}
