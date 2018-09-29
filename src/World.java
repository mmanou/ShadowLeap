import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {

	/* Tile dimensions in pixels */
	public static final float TILE_WIDTH = 48;
	public static final float TILE_HEIGHT = 48;

	/* Player starting coordinates */
	public static final float PLAYER_START_X = 512;
	public static final float PLAYER_START_Y = 720;

	/* Bus speed in pixels */
	public static final float BUS_VELOCITY = 0.15f;
	
	public static Integer currentLevel = 0;

	
	/* Stores all game Sprites that have collision effects */
	ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	ArrayList<Sprite> tileList = new ArrayList<Sprite>();
	ArrayList<Player> playerList = new ArrayList<Player>();

	public World() throws SlickException, FileNotFoundException {
		
		File lvlFile = new File("assets/levels/" + currentLevel.toString() + ".lvl");
		String[] objLine = new String[4];
		Scanner scanner = new Scanner(lvlFile);
		
		/* Coordinates of next FrogHole */
		float nextFrogHoleX = 0;
		float nextFrogHoleY = 0;
		
		while (scanner.hasNext()) {
			
			objLine = scanner.nextLine().split(",");
			System.out.println("New " + objLine[0] + ":       (" + objLine[1].toString() + ", " + objLine[2].toString() + ")");
			
			if(objLine[0].equals("water")) {
				tileList.add(new Water("assets/water.png", Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
			}
			else if(objLine[0].equals("grass")) {
				tileList.add(new Grass("assets/grass.png", Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
			}
			
			/* Initialise Trees and FrogHoles" */
			else if(objLine[0].equals("tree")) {
				tileList.add(new Tree("assets/tree.png",
						Float.valueOf(objLine[1]), Float.valueOf(objLine[2])));
				
				if (nextFrogHoleX == (Float.valueOf(objLine[1]) - (World.TILE_WIDTH * 3 / 2))
						&& nextFrogHoleY == Float.valueOf(objLine[2])) {
					spriteList.add(new FrogHole("assets/frog.png", nextFrogHoleX, nextFrogHoleY));
				}
				
				nextFrogHoleX = Float.valueOf(objLine[1]) + World.TILE_WIDTH * 3 / 2;
				nextFrogHoleY = Float.valueOf(objLine[2]);
			}
			else if(objLine[0].equals("bus")) {
				if(objLine[3].equals("false")) {
					spriteList.add(new Traffic("assets/bus.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, -BUS_VELOCITY));
				}
				else {
					spriteList.add(new Traffic("assets/bus.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, BUS_VELOCITY));
				}
			}
			else if(objLine[0].equals("bulldozer")) {
				if(objLine[3].equals("false")) {
					spriteList.add(new Bulldozer("assets/bulldozer.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, -BUS_VELOCITY));
				}
				else {
					spriteList.add(new Bulldozer("assets/bulldozer.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, BUS_VELOCITY));
				}
			}
			
			else if(objLine[0].equals("longLog")) {
				if(objLine[3].equals("false")) {
					spriteList.add(new Platform("assets/longLog.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, 240, TILE_HEIGHT, -BUS_VELOCITY));
				}
				else {
					spriteList.add(new Platform("assets/longLog.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, 240, TILE_HEIGHT, BUS_VELOCITY));
				}
			}
			
			else if(objLine[0].equals("log")) {
				if(objLine[3].equals("false")) {
					spriteList.add(new Platform("assets/log.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, 120, TILE_HEIGHT, -BUS_VELOCITY));
				}
				else {
					spriteList.add(new Platform("assets/log.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, 120, TILE_HEIGHT, BUS_VELOCITY));
				}
			}
			
			else if(objLine[0].equals("longLog")) {
				if(objLine[3].equals("false")) {
					spriteList.add(new Platform("assets/longLog.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), true, 240, TILE_HEIGHT, -BUS_VELOCITY));
				}
				else {
					spriteList.add(new Platform("assets/longLog.png",
							Float.valueOf(objLine[1]), Float.valueOf(objLine[2]), false, 240, TILE_HEIGHT, BUS_VELOCITY));
				}
			}
			
		}

		/* Initialise player */
		playerList.add(new Player("assets/frog.png", PLAYER_START_X, PLAYER_START_Y, this));		
		
	}
	
	public void initLevel(int lvl) {
		for(int i=0; i < spriteList.size(); i++) {
			
		}
	}

	public void update(Input input, int delta) {
		// Update all of the sprites in the game
		for (Sprite sprite:spriteList) {
			sprite.update(input, delta);	
		}
		for (Sprite tile:tileList) {
			tile.update(input, delta);
		}
		for (Player player: playerList) {
			player.update(input, delta);
		}

		/* Check for collision between game objects */
		for (int i = 0; i < spriteList.size() - 1; i++) {
			for (int j = i + 1; j < spriteList.size(); j++) {
				if (checkSpriteCollides(spriteList.get(i), spriteList.get(j))) {
					contactEachSprite(spriteList.get(i), spriteList.get(j), delta);
				}
			}
		}
		
		/* Check for collision between game objects and tiles */
		for (int i = 0; i < spriteList.size(); i++) {
			for (int j = 0; j < tileList.size(); j++) {
				if (checkSpriteCollides(spriteList.get(i), tileList.get(j))) {
					contactEachSprite(spriteList.get(i), tileList.get(j), delta);
				}
			}
		}
		
		/* Check for collision between players and game objects */
		for (int i = 0; i < playerList.size(); i++) {
			for (int j = 0; j < spriteList.size(); j++) {
				if (checkSpriteCollides(playerList.get(i), spriteList.get(j))) {
					contactEachSprite(playerList.get(i), spriteList.get(j), delta);
				}
			}
		}
		
		/* Check for collision between players and tiles */
		for (int i = 0; i < playerList.size(); i++) {
			for (int j = 0; j < tileList.size(); j++) {
				if (checkSpriteCollides(playerList.get(i), tileList.get(j))) {
					contactEachSprite(playerList.get(i), tileList.get(j), delta);
				}
			}
		}
	}
	
	public boolean checkValidMove(Sprite sprite) {
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
	
	public boolean checkSpriteCollides(Sprite spr1, Sprite spr2) {
		if(spr1.getBoundingBox().intersects(spr2.getBoundingBox())) {
			return true;
		}
		return false;
	}
	
	public void contactEachSprite(Sprite spr1, Sprite spr2, int delta) {
		spr1.contactSprite(spr2, delta);
		spr2.contactSprite(spr1, delta);
	}
	
	public void render(Graphics g) {
		// Draw all of the Sprites in the game
		
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
	
}
