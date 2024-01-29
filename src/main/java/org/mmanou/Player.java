package org.mmanou;

import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Takes input from and represents the player in the game.
 */
public class Player extends Sprite {
	
	/** Number of lives with which players start the game */
	public static final int INITIAL_LIVES = 3;
	/** Maximum amount of lives a Player can have at any given time */
	public static final int MAX_NUM_LIVES = 6;
	
	/* Coordinates for rendering Lives */
	private static final float LIVES_X_1 = 24;
	private static final float LIVES_X_2 = 976;
	private static final float LIVES_X_OFFSET = 32;
	private static final float LIVES_Y = 744;
	
	/** The world in which the Player exists */
	private World world;
	
	private boolean onPlatform = false;
	private static int frogHolesReached = 0;
	private int playerNumber;
	
	/* Infinity Stone inventory */
	private static boolean timeStoneCollected = false;
	private static boolean spaceStoneCollected = false;
	
	private ArrayList<Life> livesList = new ArrayList<Life>();
	
	/**
	 * @param imageSrc The path of the image to be displayed to represent this sprite in the game world.
	 * @param x The x-coordinate where this sprite spawns.
	 * @param y The y-coordinate where this sprite spawns.
	 * @param playerNum This Player's id number
	 * @param world The world object in which this Player exists
	 * @throws SlickException
	 */
	public Player(String imageSrc, float x, float y, int playerNum, World world) throws SlickException {
		super(imageSrc, x, y, false);
		this.world = world;
		this.playerNumber = playerNum;
		
		resetLives();
	}
	
	/** Creates a new Life object for the player. 
	 * @throws SlickException
	 */
	public void gainLife() throws SlickException {
		if (this.playerNumber == 1) {
			livesList.add(new Life("assets/lives.png"));
		}
		else { // Player 2
			livesList.add(new Life("assets/livesTitan.png"));
		}
	}
	
	/**
	 * @return return onPlatform
	 */
	public boolean getOnPlatform() {
		return this.onPlatform;
	}
	
	/**
	 * @param onPlatform New boolean value for onPlatform.
	 */
	public void setOnPlatform(boolean onPlatform) {
		this.onPlatform = onPlatform;
	}
	
	/** Removes a Life from the Player's livesList.
	 * If singleplayer, ends the program.
	 * If multiplayer, eliminates the player and checks if level was failed by all players.
	 */
	public void loseLife() {
		if (livesList.size() == 0) {
			if (getWorld().getNumberOfPlayers() == 1) {
				System.exit(0);
			}
		}
		else {
			livesList.remove(livesList.size() - 1);
		}
	}
	
	/** Gives a player the initial number of lives 
	 * @throws SlickException
	 */
	public void resetLives() throws SlickException {
		while (livesList.size() < INITIAL_LIVES) {
			gainLife();
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		if (frogHolesReached == World.NUM_FROG_HOLES || livesList.size() <= 0) {
			return;
		}
		
		this.setOnPlatform(false);

		/* Player movement */
		if (playerNumber == 1) { // Player 1 hotkeys
			if (input.isKeyPressed(Input.KEY_LEFT)) {
				moveLeft(input, delta);
			}

			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				moveRight(input, delta);
			}

			if (input.isKeyPressed(Input.KEY_UP)) {
				moveUp(input, delta);
			}

			if (input.isKeyPressed(Input.KEY_DOWN)) {
				moveDown(input, delta);
			}
			if (input.isKeyPressed(Input.KEY_RCONTROL)) {
				if (spaceStoneCollected == true) {
					try {
						shootLaserBolt();
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else { // Player 2 hotkeys
			if (input.isKeyPressed(Input.KEY_A)) {
				moveLeft(input, delta);
			}

			if (input.isKeyPressed(Input.KEY_D)) {
				moveRight(input, delta);
			}

			if (input.isKeyPressed(Input.KEY_W)) {
				moveUp(input, delta);
			}

			if (input.isKeyPressed(Input.KEY_S)) {
				moveDown(input, delta);
			}
			if (input.isKeyPressed(Input.KEY_LCONTROL)) {
				if (spaceStoneCollected == true) {
					try {
						shootLaserBolt();
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}
		}

		
		if (this.getX() < -(World.TILE_WIDTH / 2)
				|| this.getX() > App.SCREEN_WIDTH + (World.TILE_WIDTH / 2)) {
			loseLife();
			resetPosition();
		}
		
		if (getFrogHolesReached() == World.NUM_FROG_HOLES) {
			resetPosition();
		}

		super.update(input, delta);
	}
	
	/** resets x and y to their initial values for the Player object */
	public void resetPosition() {
		setX(World.PLAYER_START_X);
		setY(World.PLAYER_START_Y);
		setRotation(Sprite.FACE_UPWARD_ANGLE);
		super.update();
	}

	@Override
	public void contactSprite(Sprite other, int delta) throws SlickException {
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
		else if (other instanceof ExtraLife) {
			((ExtraLife) other).setCollected(true);
			if (livesList.size() < MAX_NUM_LIVES) {
				this.gainLife();
			}
		}
		
	}
	
	@Override
	public void render() {
		if (getFrogHolesReached() < World.NUM_FROG_HOLES && getNumberOfLives() > 0) {
			super.render();
		}
		
		if (playerNumber == 1) { // Player 1
			for (int i = 0; i < livesList.size(); i++) {								  //DELETE THE 48!!!!!!!!!!!!!
				livesList.get(i).render(LIVES_X_1 + (i * LIVES_X_OFFSET), LIVES_Y - 48);    //DELETE THE 48!!!!!!!!!!!!!
			}
		}
		else { // Player 2
			for (int i = 0; i < livesList.size(); i++) {								  //DELETE THE 48!!!!!!!!!!!!!
				livesList.get(i).render(LIVES_X_2 - (i * LIVES_X_OFFSET), LIVES_Y - 48);    //DELETE THE 48!!!!!!!!!!!!!
			}
		}
	}
	
	/** 
	 * @return frogHolesReached
	 */
	public int getFrogHolesReached() {
		return frogHolesReached;
	}
	/** Sets frogHolesReached to 0. */
	public void resetFrogHolesReached() {
		frogHolesReached = 0;
	}
	/** Increases frogHolesReached by 1. */
	public void incrementFrogHolesReached() {
		frogHolesReached++;
	}
	/** Sets frogHolesReached to the number of FrogHoles in World. */
	public void setLevelFinishedForPlayer() {
		frogHolesReached = World.NUM_FROG_HOLES; 
	}
	
	/* Directional player movement */
	private void moveLeft(Input input, int delta) {
		this.setRotation(Sprite.FACE_LEFT_ANGLE);
		if (this.getX() > World.TILE_WIDTH) {
			this.addToX(-World.TILE_WIDTH);
			super.update(input, delta);
			if (getWorld().checkValidPosition(this) == false) {
				this.addToX(World.TILE_WIDTH);
			}
		}
	}
	private void moveRight(Input input, int delta) {
		this.setRotation(Sprite.FACE_RIGHT_ANGLE);
		if (this.getX() < App.SCREEN_WIDTH - World.TILE_WIDTH) {
			this.addToX(World.TILE_WIDTH);
			super.update(input, delta);
			if (getWorld().checkValidPosition(this) == false) {
				this.addToX(-World.TILE_WIDTH);
			}
		}
	}
	private void moveUp(Input input, int delta) {
		this.setRotation(Sprite.FACE_UPWARD_ANGLE);
		if (this.getY() > 0) {
			this.addToY(-World.TILE_WIDTH);
			super.update(input, delta);
			if (getWorld().checkValidPosition(this) == false) {
				this.addToY(World.TILE_WIDTH);
			}
		}
	}
	private void moveDown(Input input, int delta) {
		this.setRotation(Sprite.FACE_DOWNWARD_ANGLE);
		if (this.getY() < App.SCREEN_HEIGHT - World.TILE_WIDTH) {
			this.addToY(World.TILE_WIDTH);
			super.update(input, delta);
			if (getWorld().checkValidPosition(this) == false) {
				this.addToY(-World.TILE_WIDTH);
			}
		}
	}
	
	private void shootLaserBolt() throws SlickException {
		this.setRotation(Sprite.FACE_UPWARD_ANGLE);
		getWorld().spriteList.add(new Laserbolt("assets/laserbolt.png", getX(), getY() - World.TILE_WIDTH / 2) );
	}

	/**
	 * @return timeStoneCollected
	 */
	public static boolean isTimeStoneCollected() {
		return timeStoneCollected;
	}
	/**
	 * @param timeStoneCollected Set new boolean value of timeStoneCollected.
	 */
	public static void setTimeStoneCollected(boolean timeStoneCollected) {
		Player.timeStoneCollected = timeStoneCollected;
	}
	
	/**
	 * @return spaceStoneCollected
	 */
	public static boolean isSpaceStoneCollected() {
		return spaceStoneCollected;
	}
	/**
	 * @param spaceStoneCollected Set new boolean value of spaceStoneCollected.
	 */
	public static void setSpaceStoneCollected(boolean spaceStoneCollected) {
		Player.spaceStoneCollected = spaceStoneCollected;
	}

	/**
	 * @return world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * @return number of lives in livesList
	 */
	public int getNumberOfLives() {
		return livesList.size();
	}
}
