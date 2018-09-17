import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Life {
	private Image image;
	
	/*
	Life(String type) throws SlickException {
		if (type.equals("Soul")) {
			setImage(new Image(""));
		}
		else {
			Life();
		}
	}
	*/
	Life() throws SlickException {
		setImage(new Image("assets/lives.png"));
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
