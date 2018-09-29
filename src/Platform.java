import org.newdawn.slick.SlickException;

public class Platform extends Vehicle {

	public Platform(String imageSrc, float x, float y, boolean flipImg, 
			float width, float height, float velocity) throws SlickException {
			super(imageSrc, x, y, flipImg, width, height, velocity);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Player) {
			other.addToX((float)(velocity * delta));
		}
	}
	
	
}
