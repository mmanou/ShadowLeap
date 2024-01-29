package org.mmanou;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Projectile which destroys Boulder objects.
 */
public class Laserbolt extends Sprite {
	private static final float BOLT_VELOCITY = 0.9f;
	private static final float LIVE_TIME = 200;
	private static final float BOLT_WIDTH = 12;
	private static final float BOLT_HEIGHT = 24;
	
	private double velocity;
	private float timer = 0;
	
	public Laserbolt(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false, BOLT_WIDTH, BOLT_HEIGHT);
		this.velocity = -BOLT_VELOCITY;
	}
	
	/** 
	 * @return velocity
	 */
	public double getVelocity() {
		return this.velocity;
	}
	
	@Override
	public void update(Input input, int delta) {
		addToY((float)(velocity * delta));
		
		timer += delta;
		
		if (timer >= LIVE_TIME) {
			this.setMarkedForDeath(true);
		}
		
		super.update(input, delta);
	}

	@Override
	public void contactSprite(Sprite other, int delta) throws SlickException {
		if (other.isSolid()) {
			this.setMarkedForDeath(true);
		}
		
		
	}
}
