package org.mmanou;

import java.io.FileNotFoundException;

import org.newdawn.slick.SlickException;

/**
 * Proceeds the game to the specified next level when interacted with by the player.
 */
public class NextLvlBox extends Sprite {
	int nextLvl;
	
	public NextLvlBox(String imageSrc, float x, float y, int nextLvl) throws SlickException {
		super(imageSrc, x, y, false);
		this.nextLvl = nextLvl;
	}

	@Override
	public void contactSprite(Sprite other, int delta) throws SlickException {
		if (other instanceof Player) {
			setMarkedForDeath(true);
			try {
				((Player) other).getWorld().initLevel(nextLvl);
			} catch (NumberFormatException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}


}
