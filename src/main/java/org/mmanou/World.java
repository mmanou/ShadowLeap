package org.mmanou;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Stores all game objects and handles all game elements and features
 */
public class World {

	/** Tile width in pixels */
	public static final float TILE_WIDTH = 48;
	/** Tile height in pixels */
	public static final float TILE_HEIGHT = 48;

	/** Player starting x-coordinate */
	public static final float PLAYER_START_X = 512;
	/** Player starting y-coordinate */
	public static final float PLAYER_START_Y = 720;
	
	/* Player id numbers */
	private static final int PLAYER_1_NUMBER = 1;
	private static final int PLAYER_2_NUMBER = 2;

	/* Vehicle speeds in pixels */
	private static final float BUS_SPEED = 0.15f;
	private static final float RACECAR_SPEED = 0.5f;
	private static final float BIKE_SPEED = 0.2f;
	private static final float BULLDOZER_SPEED = 0.05f;
	private static final float LOG_SPEED = 0.1f;
	private static final float LONG_LOG_SPEED = 0.07f;
	private static final float TURTLE_SPEED = 0.085f;
	
	/* Extra life spawn times */
	private static final int EXTRA_LIFE_SPAWN_TIME_MIN = 25;
	private static final int EXTRA_LIFE_SPAWN_TIME_RANGE = 10;
	private static final int EXTRA_LIFE_LIVE_TIME = 14;
	private static final int MILLISECONDS_TO_SECONDS = 1000;
	
	/** Number of FrogHoles to be reached in each level */
	public static final int NUM_FROG_HOLES = 5;
	
	private static int currentLvl = -2;
	
	private int extraLifeSpawnTime;
	private long extraLifeTimer = 0;
	
	/** Stores all non-tile and non-player game Sprites */
	public ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	/** Stores all game Tiles */
	public ArrayList<Sprite> tileList = new ArrayList<Sprite>();
	/** Stores all Player objects */
	private ArrayList<Player> playerList = new ArrayList<Player>();

	/** Creates the World object */
	public World() throws SlickException, NumberFormatException, FileNotFoundException {
		/* Initialise Player 1 */
		playerList.add(new Player("assets/frog.png", PLAYER_START_X , PLAYER_START_Y, PLAYER_1_NUMBER, this));
		
		initLevel(currentLvl);
		
	}
	
	
	/** Initialises all objects for the specified level.
	 * @param lvl Integer level number to be initialised.
	 * @throws SlickException
	 * @throws FileNotFoundException
	 * @throws NumberFormatException
	 */
	@SuppressWarnings("resource")
	public void initLevel(int lvl)
			throws SlickException, FileNotFoundException, NumberFormatException {
		File lvlFile = new File("assets/levels/" + Integer.toString(lvl) + ".lvl");
		String[] objLine = new String[4];
		Scanner scanner = new Scanner(lvlFile);
		
		/* Coordinates of next FrogHole */
		float nextFrogHoleX = 0;
		float nextFrogHoleY = 0;
		
		/* Ensure level initiated is current level */
		currentLvl = lvl;
		
		/* delete all non-Player Sprites and reset level */
		spriteList.clear();
		tileList.clear();
		resetPlayers();
		extraLifeTimer = 0;
		resetExtraLifeSpawnTime();
		
		/* Read in Sprites from .lvl file */
		while (scanner.hasNext()) {
			objLine = scanner.nextLine().split(",");
			System.out.println("New " + objLine[0] + ":     (" + objLine[1].toString() + ", " + objLine[2].toString() + ")");

			switch(objLine[0]) {
				case "water":
					tileList.add(new Water("assets/water.png",
						Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					break;
				
				case "grass":
					tileList.add(new Grass("assets/grass.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					break;
				
				case "tree":
					tileList.add(new Tree("assets/tree.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					
					if (nextFrogHoleX == (Float.valueOf(objLine[1]) - (World.TILE_WIDTH * 3 / 2))
							&& nextFrogHoleY == Float.valueOf(objLine[2])) {
						spriteList.add(new FrogHole("assets/frog.png", nextFrogHoleX, nextFrogHoleY));
					}
					
					nextFrogHoleX = Float.valueOf(objLine[1]) + World.TILE_WIDTH * 3 / 2;
					nextFrogHoleY = Float.valueOf(objLine[2]);
					break;

				case "bus":
					if(objLine[3].equals("false")) {
						spriteList.add(new Traffic("assets/bus.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, -BUS_SPEED));
					}
					else {
						spriteList.add(new Traffic("assets/bus.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, BUS_SPEED));
					}
					break;

				case "bulldozer":
					if(objLine[3].equals("false")) {
						spriteList.add(new Bulldozer("assets/bulldozer.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, -BULLDOZER_SPEED));
					}
					else {
						spriteList.add(new Bulldozer("assets/bulldozer.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, BULLDOZER_SPEED));
					}
					break;

				case "racecar":
					if(objLine[3].equals("false")) {
						spriteList.add(new Traffic("assets/racecar.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, -RACECAR_SPEED));
					}
					else {
						spriteList.add(new Traffic("assets/racecar.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, RACECAR_SPEED));
					}
					break;

				case "bike":
					if(objLine[3].equals("false")) {
						spriteList.add(new Bike("assets/bike.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, -BIKE_SPEED));
					}
					else {
						spriteList.add(new Bike("assets/bike.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, BIKE_SPEED));
					}
					break;

				case "turtle":
					if(objLine[3].equals("false")) {
						spriteList.add(new Turtle("assets/turtles.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]),
								false, 120, TILE_HEIGHT, -TURTLE_SPEED));
					}
					else {
						spriteList.add(new Turtle("assets/turtles.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]),
								true, 120, TILE_HEIGHT, TURTLE_SPEED));
					}
					break;

				case "log":
					if(objLine[3].equals("false")) {
						spriteList.add(new Platform("assets/log.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]),
								true, 120, TILE_HEIGHT, -LOG_SPEED));
					}
					else {
						spriteList.add(new Platform("assets/log.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]),
								false, 120, TILE_HEIGHT, LOG_SPEED));
					}
					break;

				case "longLog":
					if(objLine[3].equals("false")) {
						spriteList.add(new Platform("assets/longLog.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]),
								true, 240, TILE_HEIGHT, -LONG_LOG_SPEED));
					}
					else {
						spriteList.add(new Platform("assets/longLog.png",
								Float.valueOf(objLine[1]), Float.valueOf(objLine[2]),
								false, 240, TILE_HEIGHT, LONG_LOG_SPEED));
					}
					break;
					
				case "boulder":
					tileList.add(new Boulder("assets/boulder.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					break;
				
				case "banner_gamemode":
					spriteList.add(new Sprite("assets/banner_selectGameMode.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false));
					break;

				case "banner_loadgame":
					spriteList.add(new Sprite("assets/banner_loadSavedGame.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false));
					break;

				case "box_singleplayer":
					spriteList.add(new NextLvlBox("assets/box_singleplayer.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), currentLvl + 1));
					break;

				case "box_multiplayer":
					spriteList.add(new MPBox("assets/box_multiplayer.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					break;

				case "box_newGame":
					spriteList.add(new NextLvlBox("assets/box_newGame.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), currentLvl + 1));
					break;

				case "box_loadGame":
					spriteList.add(new LoadGameBox("assets/box_loadGame.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), "saves/savegame.txt"));
					break;
				case "timeStone":
					spriteList.add(new InfinityStone("time",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					break;
				case "spaceStone":
					spriteList.add(new InfinityStone("space",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
					break;
			}
		}
	}
	
	/** Saves current progress in saves/savegame.txt
	 * @throws IOException 
	 */
	public void saveProgress() throws IOException {
		PrintWriter pw = new PrintWriter(new File("saves/savegame.txt"));
		pw.printf("currentLvl:" + ((currentLvl >= 0)?currentLvl:0)	// prints the higher of 0 and currentLvl
				+ "\ncurrentLifeTotal:" + (playerList.get(0).getNumberOfLives())
				+ "\ntimeStoneCollected:" + Player.isTimeStoneCollected()
				+ "\nspaceStoneCollected:" + Player.isSpaceStoneCollected()
				);
		pw.close();
	}
	

	/** Updates all game objects, and checks if new objects need to be created.
	 * @param input Slick Input object, observes for user input.
	 * @param delta Time since last screen refresh, in milliseconds.
	 */
	public void update(Input input, int delta)
			throws NumberFormatException, FileNotFoundException, SlickException {
		
		int playersFinished = 0;
		int playersEliminated = 0;
		
		if (input.isKeyPressed(Input.KEY_1)
				&& getNumberOfPlayers() == 1
				&& Player.isTimeStoneCollected()) {
			try {
				saveProgress();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/* Check for level end */
		for (Player player:playerList) {
			if (player.getFrogHolesReached() >= NUM_FROG_HOLES) {
				playersFinished++;
			}
			if (player.getNumberOfLives() <= 0) {
				playersEliminated++;
			}
		}
		if (playersFinished >= getNumberOfPlayers()) {
			initLevel(++currentLvl);
		}
		if (playersEliminated >= getNumberOfPlayers()) {
			System.exit(1);
		}
		

		extraLifeTimer += delta;

		if (playersFinished == 0 && extraLifeTimer >= extraLifeSpawnTime) {
			int platformIndex;
			try {
				platformIndex = getRandomPlatformIndex(spriteList);
			} catch (NullPointerException e) {
				platformIndex = -1;
			}
			if (platformIndex >= 0) {
				spriteList.add(new ExtraLife("assets/extralife.png",
						 spriteList.get(platformIndex).getX(),
						 spriteList.get(platformIndex).getY(),
						 spriteList.get(platformIndex).getWidth()));
				
				extraLifeTimer = 0;
				resetExtraLifeSpawnTime();
			}
		}
		
		/* Update all of the sprites in the game */
		for (Sprite sprite:spriteList) {
			sprite.update(input, delta);
			
			if (sprite instanceof ExtraLife) {
				if (((ExtraLife) sprite).getTimer() >= EXTRA_LIFE_LIVE_TIME * MILLISECONDS_TO_SECONDS
					 || ((ExtraLife) sprite).isCollected() == true) {
					sprite.setMarkedForDeath(true);
				}
			}
		}
		
		killMarkedSprites(spriteList);
		killMarkedSprites(tileList);

		
		for (Sprite tile:tileList) {
			tile.update(input, delta);
		}
		for (Player player: playerList) {
			player.update(input, delta);
		}

		/* Check for collision between game objects */
		checkCollisionWithinList(spriteList, delta);
		checkCollisionBetweenSpriteLists(spriteList, tileList, delta);
		checkCollisionWithPlayerList(playerList, spriteList, delta);
		checkCollisionWithPlayerList(playerList, tileList, delta);
		
	}
	
	/** sets frogHolesReached and position to their defaults for each player in playerList. */
	public void resetPlayers() {
		for (Player player:playerList) {
			player.resetFrogHolesReached();
			player.resetPosition();
		}
	}
	
	/** checks if sprite has moved into a space already filled by solid Sprite.
	 * @param sprite Sprite object to be checked.
	 * @return true if the sprite is in a valid position, else false.
	 */
	public boolean checkValidPosition(Sprite sprite) {
		for (int i = 0; i < spriteList.size(); i++) {
			if (spriteList.get(i).isSolid() == true) {
				if (checkSpriteCollides(sprite, spriteList.get(i))) {
					return false;
				}
			}
		}
		for (int i = 0; i < tileList.size(); i++) {
			if (tileList.get(i).isSolid() == true) {
				if (checkSpriteCollides(sprite, tileList.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/** Checks if two Sprites collide.
	 * @return true if spr1 and spr2 collide, false otherwise.
	 */
	public boolean checkSpriteCollides(Sprite spr1, Sprite spr2) {
		if(spr1.getBoundingBox().intersects(spr2.getBoundingBox())) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return number of Players in playerList().
	 */
	public int getNumberOfPlayers() {
		return playerList.size();
	}
	
	private void contactEachSprite(Sprite spr1, Sprite spr2, int delta) throws SlickException {
		spr1.contactSprite(spr2, delta);
		spr2.contactSprite(spr1, delta);
	}
	
	/** Draws all of the Sprites in the game.
	 * @param g Slick graphics object.
	 */
	public void render(Graphics g) {
		/* Draw tiles */
		for (Sprite sprite:tileList) {
			sprite.render();
		}
		
		/* Draw game objects */
		for (Sprite sprite:spriteList) {
			sprite.render();
		}
		
		/* Draw players */
		for (Player player:playerList) {
			player.render();
		}
	}
	
	private void checkCollisionBetweenSpriteLists(ArrayList<Sprite> list1, ArrayList<Sprite> list2, int delta) throws SlickException {
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (checkSpriteCollides(list1.get(i), list2.get(j))) {
					contactEachSprite(list1.get(i), list2.get(j), delta);
				}
			}
		}
	}
	
	private void checkCollisionWithPlayerList(ArrayList<Player> playerList, ArrayList<Sprite> spriteList, int delta) throws SlickException {
		for (int i = 0; i < playerList.size(); i++) {
			for (int j = 0; j < spriteList.size(); j++) {
				if (checkSpriteCollides(playerList.get(i), spriteList.get(j))) {
					contactEachSprite(playerList.get(i), spriteList.get(j), delta);
				}
			}
		}
	}
	
	private void checkCollisionWithinList(ArrayList<Sprite> list, int delta) throws SlickException {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (checkSpriteCollides(list.get(i), list.get(j))) {
					contactEachSprite(list.get(i), list.get(j), delta);
				}
			}
		}
	}
	
	private int getRandomPlatformIndex(ArrayList<Sprite> spriteList) throws NullPointerException {
		if (checkListForPlatform(spriteList) == false) {
			throw new NullPointerException("list does not contain a Platform object: " + spriteList);
		}
		Random random = new Random();
		int index;
		do {
			index = random.nextInt(spriteList.size());
		} while (!(spriteList.get(index) instanceof Platform));
		
		return index;
	}
	
	private boolean checkListForPlatform(ArrayList<Sprite> spriteList) {
		for (Sprite sprite:spriteList) {
			if (sprite instanceof Platform) {
				return true;
			}
		}
		return false;
	}
	
	private void resetExtraLifeSpawnTime() {
		Random random = new Random();
		extraLifeSpawnTime = (random.nextInt(EXTRA_LIFE_SPAWN_TIME_RANGE) + EXTRA_LIFE_SPAWN_TIME_MIN) * MILLISECONDS_TO_SECONDS;
	}
	
	private void killMarkedSprites(ArrayList<Sprite> spriteList) {
		int i = 0;
		while(i < spriteList.size()) {
			if (spriteList.get(i).isMarkedForDeath() == true) {
				spriteList.remove(i);
			}
			else {
				i++;
			}
		}
	}

	/** Adds a second Player object to playerList.
	 * @throws SlickException
	 */
	public void createSecondPlayer() throws SlickException {
		System.out.println("1");
		if (this.getNumberOfPlayers() == 1) {
			playerList.add(new Player("assets/frogTitan.png", PLAYER_START_X, PLAYER_START_Y, PLAYER_2_NUMBER, this));
		}
		try {
			initLevel(currentLvl = 0);
		} catch (NumberFormatException | FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	/** Loads a save file.
	 * @param fileSrc The location of the save file
	 * @throws SlickException
	 */
	public void loadSaveFile(String fileSrc) throws SlickException {
		int savedLifeTotal = 3;
		String[] nextLine = new String[2];
		File saveFile = new File(fileSrc);
		Scanner scanner = null;
		try {
			scanner = new Scanner(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		while (scanner.hasNext()) {
			nextLine = scanner.nextLine().split(":");
			switch (nextLine[0]) {
				case "spaceStoneCollected":
					Player.setSpaceStoneCollected(Boolean.parseBoolean(nextLine[1]));
					break;
				case "timeStoneCollected":
					Player.setTimeStoneCollected(Boolean.parseBoolean(nextLine[1]));
					break;
				case "currentLvl":
					currentLvl = Integer.parseInt(nextLine[1]);
					break;
				case "currentLifeTotal":
					savedLifeTotal = Integer.parseInt(nextLine[1]);
					break;
			}
		}
		
		try {
			initLevel(currentLvl);
		} catch (NumberFormatException | FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Set correct number of lives for Player */
		while (savedLifeTotal < playerList.get(0).getNumberOfLives()) {
			playerList.get(0).loseLife();
		}
		while (savedLifeTotal > playerList.get(0).getNumberOfLives()) {
			playerList.get(0).gainLife();
		}
	}
	
}
