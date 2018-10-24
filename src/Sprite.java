import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;
import org.newdawn.slick.Image;

public class Sprite {
	/* Angles in degrees, to represent the direction in which a Sprite's image should be pointed */
	public static final float FACE_UPWARD_ANGLE = 0;
	public static final float FACE_RIGHT_ANGLE = 90;
	public static final float FACE_DOWNWARD_ANGLE = 180;
	public static final float FACE_LEFT_ANGLE = 270;

	private float x;
	private float y;
	private float width = World.TILE_WIDTH;
	private float height = World.TILE_WIDTH;
	private boolean isSolid = false;
	private boolean markedForDeath = false; // Indicates if this object should be deleted from the game world
	private boolean isImageFlipped;
	private Image image;
	private BoundingBox bBox;
	

	/** Create Sprite, with same width and height for Sprite and Image.
	 * @param imageSrc The path of the image to be displayed to represent this sprite in the game world.
	 * @param x The x-coordinate where this sprite spawns.
	 * @param y The y-coordinate where this sprite spawns.
	 * @param flipImg whether the image for this sprite should be flipped along it's vertical axis
	 * @throws SlickException
	 */
	public Sprite(String imageSrc, float x, float y, boolean flipImg)
			throws SlickException {
		if  (flipImg == true) {
			isImageFlipped = true;
			image = new Image(imageSrc).getFlippedCopy(true, false);
		}
		else {
			isImageFlipped = false;
			image = new Image(imageSrc);
		}
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		this.setX(x);
		this.setY(y);
		bBox = new BoundingBox(image, x, y);
	}
	
	/** Create Sprite, with different width and height for Sprite and Image.
	 * @param imageSrc The path of the image to be displayed to represent this sprite in the game world.
	 * @param x The x-coordinate where this sprite spawns.
	 * @param y The y-coordinate where this sprite spawns.
	 * @param flipImg whether the image for this sprite should be flipped along it's vertical axis.
	 * @param width The width of the associated boundingBox
	 * @param height The height of the associated boundingBox
	 * @throws SlickException
	 */
	public Sprite(String imageSrc, float x, float y, boolean flipImg, float width, float height)
			throws SlickException {
		if  (flipImg == true) {
			image = new Image(imageSrc).getFlippedCopy(true, false);
		}
		else {
			image = new Image(imageSrc);
		}
		this.setWidth(width);
		this.setHeight(height);
		this.setX(x);
		this.setY(y);
		bBox = new BoundingBox(x, y, width, height);
	}
	
	/**
	 * @return isImageFlipped
	 */
	public boolean isImageFlipped() {
		return isImageFlipped;
	}

	/** Flips image of sprite across its vertical axis. */
	public void flipImage() {
		if (isImageFlipped == true) {
			isImageFlipped = false;
		}
		else {
			isImageFlipped = true;
		}
		image = image.getFlippedCopy(true, false);
	}

	/** Updates boundingBox for Sprite
	 * @param input Slick Input object, observes for user input.
	 * @param delta Time since last screen refresh, in milliseconds.
	 */
	public void update(Input input, int delta) {
		update();
	}
	
	/** Updates boundingBox for Sprite */
	public void update() {
		bBox.setX(x);
		bBox.setY(y);
	}

	/** Draws Sprite on the screen */
	public void render() {
		image.drawCentered(x, y);
	}
	
	/** Called when a collision takes place between two Sprites
	 * @param other The Sprite object with which this object is contacting.
	 * @param delta Time since last screen refresh, in milliseconds.
	 * @throws SlickException
	 */
	public void contactSprite(Sprite other, int delta) throws SlickException {
	}

	/**
	 * @return x
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * @param x New x-coordinate.
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * @param x Value added to current x-coordinate.
	 */
	public void addToX(float x) {
		this.x += x;
	}
	
	/**
	 * @return y
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * @param x New y-coordinate.
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @param x Value added to current y-coordinate.
	 */
	public void addToY(float y) {
		this.y += y;
	}

	/**
	 * @return bBox
	 */
	public BoundingBox getBoundingBox() {
		return bBox;
	}

	/**
	 * @param rotationAngle New rotation angle, in degrees, of image from 0.
	 */
	public void setRotation(float rotationAngle) {
		image.setRotation(rotationAngle);
	}

	/**
	 * @return isSolid
	 */
	public boolean isSolid() {
		return isSolid;
	}

	/**
	 * @param isSolid New boolean value of isSolid.
	 */
	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	/**
	 * @return width
	 */
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return height
	 */
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return isMarkedForDeath
	 */
	public boolean isMarkedForDeath() {
		return markedForDeath;
	}
	
	/**
	 * @param markedForDeath New boolean value of markedForDeath.
	 */
	public void setMarkedForDeath(boolean markedForDeath) {
		this.markedForDeath = markedForDeath;
	}
	
}
