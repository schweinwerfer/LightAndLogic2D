package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.gfx.SpriteSheet;
import de.ora.game.ext.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

	private final BufferedImage image;

	public Block(int x, int y, SpriteSheet spriteSheet) {
		super(ObjectIdImpl.BLOCK, x, y,1, spriteSheet);
		image = getImage(1, 1);
	}

	public void internalTick() {

	}

	@Override
	public void render(Graphics2D g, Renderer renderer) {
		g.drawImage(image, x, y, null);
//		g.setColor(new Color(0xD4D4D4));
//		g.fillRect(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
