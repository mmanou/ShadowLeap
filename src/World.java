import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {

	/* Tile dimensions in pixels */
	public static final float TILE_WIDTH = 48;
	public static final float TILE_HEIGHT = 48;

	/* Height of the water section in tiles */
	public static final int WATER_SECTION_HEIGHT = 8;

	/* Y Coordinates of the grass rows in pixels */
	public static final int GRASS_Y_1 = 672;
	public static final int GRASS_Y_2 = 384;

	/* Player starting coordinates */
	public static final float PLAYER_START_X = 512;
	public static final float PLAYER_START_Y = 720;

	/* Y Coordinates of bus rows in pixels */
	public static final float BUS_ROW_1_Y = 432;
	public static final float BUS_ROW_2_Y = 480;
	public static final float BUS_ROW_3_Y = 528;
	public static final float BUS_ROW_4_Y = 576;
	public static final float BUS_ROW_5_Y = 624;

	/* X Coordinate offsets of buses in pixels */
	public static final float BUS_ROW_1_OFFSET = 48;
	public static final float BUS_ROW_2_OFFSET = 0;
	public static final float BUS_ROW_3_OFFSET = 64;
	public static final float BUS_ROW_4_OFFSET = 128;
	public static final float BUS_ROW_5_OFFSET = 250;

	/* Separation distance between buses in tile widths */
	public static final float BUS_ROW_1_SEP = 6.5f;
	public static final float BUS_ROW_2_SEP = 5;
	public static final float BUS_ROW_3_SEP = 12;
	public static final float BUS_ROW_4_SEP = 5;
	public static final float BUS_ROW_5_SEP = 6.5f;

	/* Bus speed in pixels */
	public static final float BUS_VELOCITY = 0.15f;
	
	private Image grass;

	/* Stores all game Sprites that have collision effects */
	ArrayList<Sprite> spriteList = new ArrayList<Sprite>();

	public World() throws SlickException {
		/* Initialise background */
		grass = new Image("assets/grass.png");

		/* Initialise player */
		spriteList.add(new Player("assets/frog.png", PLAYER_START_X, PLAYER_START_Y));		
		
		/* Initialise buses */
		for (float x = BUS_ROW_1_OFFSET; x <= App.SCREEN_WIDTH; x += TILE_WIDTH * BUS_ROW_1_SEP) {
			spriteList.add(new Traffic("assets/bus.png", x, BUS_ROW_1_Y, true, -BUS_VELOCITY));
		}
		for (float x = BUS_ROW_2_OFFSET; x <= App.SCREEN_WIDTH; x += TILE_WIDTH * BUS_ROW_2_SEP) {
			spriteList.add(new Traffic("assets/bus.png", x, BUS_ROW_2_Y, false, BUS_VELOCITY));
		}
		for (float x = BUS_ROW_3_OFFSET; x <= App.SCREEN_WIDTH; x += TILE_WIDTH * BUS_ROW_3_SEP) {
			spriteList.add(new Traffic("assets/bus.png", x, BUS_ROW_3_Y, true, -BUS_VELOCITY));
		}
		for (float x = BUS_ROW_4_OFFSET; x <= App.SCREEN_WIDTH; x += TILE_WIDTH * BUS_ROW_4_SEP) {
			spriteList.add(new Traffic("assets/bus.png", x, BUS_ROW_4_Y, false, BUS_VELOCITY));
		}
		for (float x = BUS_ROW_5_OFFSET; x <= App.SCREEN_WIDTH; x += TILE_WIDTH * BUS_ROW_5_SEP) {
			spriteList.add(new Traffic("assets/bus.png", x, BUS_ROW_5_Y, true, -BUS_VELOCITY));
		}

		/* Initialise water tiles */
		for (int i = 0; i < App.SCREEN_WIDTH; i += TILE_WIDTH) {
			for (int j = 1; j < WATER_SECTION_HEIGHT; j++) {
				spriteList.add(new Water("assets/water.png", i, j * TILE_HEIGHT));
			}
		}
		
	}

	public void update(Input input, int delta) {
		// Update all of the sprites in the game
		for (Sprite sprite:spriteList) {
			sprite.update(input, delta);	
		}

		/* Check for collisions */
		for (int i = 0; i < spriteList.size() - 1; i++) {
			for (int j = i + 1; j < spriteList.size(); j++) {
				if (spriteList.get(i).getBoundingBox().intersects(spriteList.get(j).getBoundingBox())) {
					spriteList.get(i).contactSprite(spriteList.get(j));
					spriteList.get(j).contactSprite(spriteList.get(i));
					update(input, delta);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		// Draw all of the Sprites in the game
		for (int i = 0; i < App.SCREEN_WIDTH; i += TILE_WIDTH) {
			grass.drawCentered(i, GRASS_Y_1);
			grass.drawCentered(i, GRASS_Y_2);
		}
		
		for (Sprite sprite:spriteList) {
			sprite.render();
		}
	}
	
}
