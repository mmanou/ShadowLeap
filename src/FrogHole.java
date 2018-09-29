import org.newdawn.slick.SlickException;

public class FrogHole extends Sprite {
	private boolean filled = false;

	public FrogHole(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false, World.TILE_WIDTH * 2, World.TILE_HEIGHT);
	}
	
	@Override
	public void render() {
		if (filled == true) {
			super.render();
		}
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Player) {
			if (filled == false) {
				filled = true;
				((Player) other).incrementFrogHolesReached();
				((Player) other).resetPosition();
				this.setSolid(true);
			}
			else {
				((Player) other).loseLife();
				((Player) other).resetPosition();
			}
		}
	}
	public boolean getFilled() {
		return filled;
	}
}
