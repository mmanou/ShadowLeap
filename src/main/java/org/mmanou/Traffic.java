package org.mmanou;

import org.newdawn.slick.SlickException;

/**
 * Basic obstacle. Kills Players.
 */
public class Traffic extends Vehicle {

	public Traffic(String imageSrc, float x, float y, boolean flipImg, float velocity)
			throws SlickException {
		super(imageSrc, x, y, flipImg, velocity);
	}

}
