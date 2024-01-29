package org.mmanou;

import org.newdawn.slick.SlickException;

/**
 * Switches the game into Multiplayer mode when interacted with by the player.
 */
public class MPBox extends Sprite {

	public MPBox(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false);
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) throws SlickException {
		if (other instanceof Player) {
			((Player) other).getWorld().createSecondPlayer();
			setMarkedForDeath(true);
		}
	}

}
