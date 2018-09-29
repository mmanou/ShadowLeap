import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Turtle extends Vehicle {
	
	private boolean isSubmerged = false;
	private long timer = 0;

	public Turtle(String imageSrc, float x, float y, boolean flipImg,
			float width, float height, float velocity) throws SlickException {
		super(imageSrc, x, y, flipImg, width, height, velocity);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (this.isSubmerged() == false) {
			if (other instanceof Player) {
				other.addToX((float)(velocity * delta));
			}
		}
	}
	
	@Override
	public void render() {
		if (this.isSubmerged() == false) {
			super.render();
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		timer += delta;
		if (isSubmerged == false) {
			if (timer >= 7000) {
				setSubmerged(true);
				timer = 0;
			}
		}
		else if (isSubmerged == true) {
			if (timer >= 2000) {
				setSubmerged(false);
				timer = 0;
			}
		}
		super.update(input, delta);
	}

	public boolean isSubmerged() {
		return isSubmerged;
	}

	public void setSubmerged(boolean isSubmerged) {
		this.isSubmerged = isSubmerged;
	}
	
	
}
