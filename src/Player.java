import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Sprite {
	
	public static final int INITIAL_LIVES = 3;
	public static final float LIVES_X = 24;
	public static final float LIVES_X_OFFSET = 32;
	public static final float LIVES_Y = 744;
	
	private boolean onPlatform = false;
	private int frogHolesReached = 0;
	private World world;;
	
	ArrayList<Life> livesList = new ArrayList<Life>();
	
	public Player(String imageSrc, float x, float y, World world) throws SlickException {
		super(imageSrc, x, y, false);
		for (int i = 0; i < INITIAL_LIVES; i++) {
			gainLife();
		}
		this.world = world;
	}
	/*
	public void gainLife(String type) throws SlickException {
		livesList.add(new Life(type));										//Life types
	}
	*/
	public void gainLife() throws SlickException  {
		livesList.add(new Life());
	}
	
	public boolean getOnPlatform() {
		return this.onPlatform;
	}
	
	public void setOnPlatform(boolean onPlatform) {
		this.onPlatform = onPlatform;
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
		
		this.setOnPlatform(false);

		/* Player movement */
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			this.setRotation(Sprite.FACE_LEFT_ANGLE);
			if (this.getX() > World.TILE_WIDTH) {
				this.addToX(-World.TILE_WIDTH);
				super.update(input, delta);
				if (world.checkValidMove(this) == false) {
					this.addToX(World.TILE_WIDTH);
				}
			}

		}

		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			this.setRotation(Sprite.FACE_RIGHT_ANGLE);
			if (this.getX() < App.SCREEN_WIDTH - World.TILE_WIDTH) {
				this.addToX(World.TILE_WIDTH);
				super.update(input, delta);
				if (world.checkValidMove(this) == false) {
					this.addToX(-World.TILE_WIDTH);
				}
			}

			
		}

		if (input.isKeyPressed(Input.KEY_UP)) {
			this.setRotation(Sprite.FACE_UPWARD_ANGLE);
			this.addToY(-World.TILE_WIDTH);
			super.update(input, delta);
			if (world.checkValidMove(this) == false) {
				this.addToY(World.TILE_WIDTH);
			}
		}

		if (input.isKeyPressed(Input.KEY_DOWN)) {
			this.setRotation(Sprite.FACE_DOWNWARD_ANGLE);
			if (this.getY() < App.SCREEN_HEIGHT - World.TILE_WIDTH) {
				this.addToY(World.TILE_WIDTH);
				super.update(input, delta);
				if (world.checkValidMove(this) == false) {
					this.addToY(-World.TILE_WIDTH);
				}
			}
		}
		
		if (this.getX() < -(World.TILE_WIDTH / 2)
				|| this.getX() > App.SCREEN_WIDTH + (World.TILE_WIDTH / 2)) {
			loseLife();
			resetPosition();
		}
		
		if (getFrogHolesReached() == 5) {
			resetPosition();
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
	public void contactSprite(Sprite other, int delta) {
		if (other instanceof Traffic) {
			loseLife();
			resetPosition();
		}
		else if (other instanceof Platform) {
			this.setOnPlatform(true);
		}
		else if (other instanceof Turtle) {
			if (((Turtle) other).isSubmerged() == false) {
				this.setOnPlatform(true);
			}
			
		}
		else if (other instanceof Water) {
			if (this.getOnPlatform() == false) {
				loseLife();
				resetPosition();
			}
		}
		
	}
	
	@Override
	public void render() {
		if (getFrogHolesReached() < 5) {
			super.render();
		}
		
		for (int i = 0; i < livesList.size(); i++) {								  //DELETE THE 48!!!!!!!!!!!!!
			livesList.get(i).render(LIVES_X + (i * LIVES_X_OFFSET), LIVES_Y - 48);    //DELETE THE 48!!!!!!!!!!!!!
		}
	}
	public int getFrogHolesReached() {
		return frogHolesReached;
	}
	public void resetFrogHolesReached(int frogHolesReached) {
		this.frogHolesReached = 0;
	}
	public void incrementFrogHolesReached() {
		this.frogHolesReached++;
	}
	
}
