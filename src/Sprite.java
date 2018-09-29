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
	private float width = World.TILE_WIDTH;
	private float height = World.TILE_WIDTH;
	private boolean isSolid = false;
	private boolean isImageFlipped;
	private Image image;
	private BoundingBox bBox;
	

	/* Same width and height for Sprite and Image */
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
	
	/* Different width and height for Sprite and Image */
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
	
	public boolean isImageFlipped() {
		return isImageFlipped;
	}
	public void flipImage() {
		if (isImageFlipped == true) {
			isImageFlipped = false;
		}
		else {
			isImageFlipped = true;
		}
		image = image.getFlippedCopy(true, false);
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

	public void contactSprite(Sprite other, int delta) {
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

	public boolean isSolid() {
		return isSolid;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
