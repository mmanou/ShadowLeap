import org.newdawn.slick.SlickException;


/**
 * Loads a savegame file when interacted with by the player.
 */
public class LoadGameBox extends Sprite {
	private String fileSrc;

	public LoadGameBox(String imageSrc, float x, float y, String fileSrc) throws SlickException {
		super(imageSrc, x, y, false);
		this.fileSrc = fileSrc;
	}

	@Override
	public void contactSprite(Sprite other, int delta) throws SlickException {
		if (other instanceof Player) {
			setMarkedForDeath(true);
			((Player) other).getWorld().loadSaveFile(fileSrc);
		}
	}
}
