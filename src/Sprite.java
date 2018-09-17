import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;
import org.newdawn.slick.Image;

public class Sprite {
	public static final float FACE_UPWARD_ANGLE = 0;
	public static final float FACE_RIGHT_ANGLE = 90;
	public static final float FACE_DOWNWARD_ANGLE = 180;
	public static final float FACE_LEFT_ANGLE = 270;

	private float x;
	private float y;
	private Image image;
	private BoundingBox bBox;

	/* Constant image */
	public Sprite(String imageSrc, float x, float y, boolean flipImg) throws SlickException {
		if  (flipImg == true) {
			image = new Image(imageSrc).getFlippedCopy(true, false);
		}
		else {
			image = new Image(imageSrc);
		}
		bBox = new BoundingBox(x, y,  World.TILE_WIDTH, World.TILE_HEIGHT);
		this.setX(x);
		this.setY(y);
	}

	public void update(Input input, int delta) {
		update();
	}
	public void update() {
		bBox.setX(x);
		bBox.setY(y);
	}

	public void render() {
		image.drawCentered(x, y);
	}

	public void contactSprite(Sprite other) {
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void addToX(float x) {
		this.x += x;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void addToY(float y) {
		this.y += y;
	}

	public BoundingBox getBoundingBox() {
		return bBox;
	}

	public void setRotation(float rotationAngle) {
		image.setRotation(rotationAngle);
	}
	
}
