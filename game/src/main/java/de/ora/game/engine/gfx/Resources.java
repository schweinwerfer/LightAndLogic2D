package de.ora.game.engine.gfx;

import de.ora.game.engine.Handler;
import de.ora.game.gos.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Resources {

	private final SpriteSheet spriteSheet;
	private Handler handler;

	public Resources(Handler handler) {
		this.handler = handler;
		ImageLoader imageLoader = new ImageLoader();
		BufferedImage level = imageLoader.load("levels/level1.png");
		spriteSheet = new SpriteSheet(ImageLoader.load("images/spritesheet.png"));
		loadLevel(level);
	}

	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0x0ff;
				int green = (pixel >> 8) & 0x0ff;
				int blue = (pixel) & 0x0ff;

				Color color = new Color(red, green, blue);

				if(Color.BLACK.equals(color)) {
					continue;
				}

				if(ObjectIdImpl.BLOCK.matches(color)) {
					handler.add(new Block(xx * 32, yy * 32, spriteSheet));
				}
				else if(ObjectIdImpl.PLAYER.matches(color)) {
					handler.add(new Player(xx * 32, yy * 32));
				}
				else if(ObjectIdImpl.BOX.matches(color)) {
					handler.add(new Box(xx * 32, yy * 32, spriteSheet));

				}
				else if(ObjectIdImpl.ENEMY.matches(color)) {
					handler.add(new Enemy(xx * 32, yy * 32));
				}

			}
		}
	}


}
