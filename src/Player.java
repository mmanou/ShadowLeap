import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Sprite {
	
	public static final int INITIAL_LIVES = 3;
	public static final float LIVES_X = 24;
	public static final float LIVES_X_OFFSET = 32;
	public static final float LIVES_Y = 744;
	
	private boolean onLog = false;
	
	ArrayList<Life> livesList = new ArrayList<Life>();
	
	public Player(String imageSrc, float x, float y) throws SlickException {
		super(imageSrc, x, y, false);
		for (int i = 0; i < INITIAL_LIVES; i++) {
			gainLife();
		}
	}
	/*
	public void gainLife(String type) throws SlickException {
		livesList.add(new Life(type));										//Life types
	}
	*/
	public void gainLife() throws SlickException  {
		livesList.add(new Life());
	}
	
	public boolean getOnLog() {
		return this.onLog;
	}
	
	public void setOnLog(boolean onLog) {
		this.onLog = onLog;
	}
	
	public void loseLife() {
		if (livesList.size() == 0) {
			System.exit(0);
		}
		else {
			livesList.remove(livesList.size() - 1);
		}
	}

	@Override
	public void update(Input input, int delta) {

		/* Player movement */
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			this.setRotation(Sprite.FACE_LEFT_ANGLE);
			if (this.getX() > World.TILE_WIDTH) {
				this.addToX(-World.TILE_WIDTH);
			}
		}

		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			this.setRotation(Sprite.FACE_RIGHT_ANGLE);
			if (this.getX() < App.SCREEN_WIDTH - World.TILE_WIDTH) {
				this.addToX(World.TILE_WIDTH);
			}
		}

		if (input.isKeyPressed(Input.KEY_UP)) {
			this.setRotation(Sprite.FACE_UPWARD_ANGLE);
			this.addToY(-World.TILE_WIDTH);
		}

		if (input.isKeyPressed(Input.KEY_DOWN)) {
			this.setRotation(Sprite.FACE_DOWNWARD_ANGLE);
			if (this.getY() < App.SCREEN_HEIGHT - World.TILE_WIDTH) {
				this.addToY(World.TILE_WIDTH);
			}
		}

		super.update(input, delta);
	}
	
	public void resetPosition() {
		setX(World.PLAYER_START_X);
		setY(World.PLAYER_START_Y);
		setRotation(Sprite.FACE_UPWARD_ANGLE);
		super.update();
	}

	@Override
	public void contactSprite(Sprite other) {
		if (other instanceof Traffic) {
			loseLife();
			resetPosition();
		}
		
		if (other instanceof Water) {
			if (this.onLog == false) {
				loseLife();
				resetPosition();
			}
		}
	}
	
	@Override
	public void render() {
		super.render();
		for (int i = 0; i < livesList.size(); i++) {								  //DELETE THE 48!!!!!!!!!!!!!
			livesList.get(i).render(LIVES_X + (i * LIVES_X_OFFSET), LIVES_Y - 48);    //DELETE THE 48!!!!!!!!!!!!!
		}
	}
}
