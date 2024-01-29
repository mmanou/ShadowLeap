package org.mmanou;

import org.newdawn.slick.SlickException;

/**
 * Stationary obstacle. Can be avoided by Players if they maintain contact with a Platform.
 */
public class Water extends Sprite {

	public Water(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false);
	}
}
