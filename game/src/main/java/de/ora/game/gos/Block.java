package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.gfx.Image;
import de.ora.game.engine.gfx.SpriteSheet;
import de.ora.game.ext.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

	private final BufferedImage image;
	private final Image image2;

	public Block(int x, int y, SpriteSheet spriteSheet) {
		super(ObjectIdImpl.BLOCK, x, y,1, spriteSheet);
		image = getImage(1, 1);
		image2 = new Image(image);
		setBlockLight(true);
	}

	public void internalUpdate(double passedTime) {

	}

	@Override
	public void render(Graphics2D g, Renderer renderer) {
		renderer.draw(image2, x, y);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

	@Override
	public Rectangle getBlockBounds() {
		return new Rectangle(x + 2, y + 2, 28, 28);
	}
}
