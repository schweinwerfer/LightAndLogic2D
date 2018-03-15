package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.gfx.SpriteSheet;
import de.ora.game.ext.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
	private BufferedImage image;

	public Box(int x, int y, SpriteSheet spriteSheet) {
		super(ObjectIdImpl.BOX, x, y, 2,spriteSheet);
		image = getImage(2, 1);
	}

	public void internalTick() {

	}

	@Override
	public void render(Graphics2D g, Renderer renderer) {
		g.drawImage(image, x, y, null);
	}

	public Rectangle getBounds() {
		return null;
	}
}
