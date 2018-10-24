import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the number of mistakes allowed to a player before they are eliminated.
 * One is deleted whenever the Player contacts an obstacle.
 */
public class Life {
	private Image image;

	Life(String imageSrc) throws SlickException {
		setImage(new Image(imageSrc));
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public void render(float x, float y) {
		image.draw(x, y);
	}
}
