package org.mmanou;

import org.newdawn.slick.SlickException;

/**
 * Power-up which grants players new abilities.
 */
public class InfinityStone extends Sprite {
	private String stoneName;

	public InfinityStone(String name, float x, float y) throws SlickException {
		super("assets/" + name + "Stone.png", x, y, false);
		this.stoneName = name;
	}
	
	@Override
	public void contactSprite(Sprite other, int delta) throws SlickException {
		if (other instanceof Player) {
			this.setMarkedForDeath(true);
			
			switch (stoneName) {
				case "space":
					Player.setSpaceStoneCollected(true);
					break;
				case "time":
					Player.setTimeStoneCollected(true);
					break;
			}
			
			((Player) other).setLevelFinishedForPlayer();
		}
	}

}
