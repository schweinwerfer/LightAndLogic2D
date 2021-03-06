package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.gfx.Image;
import de.ora.game.engine.gfx.SpriteSheet;
import de.ora.game.ext.Renderer;

import java.awt.*;

public class Box extends GameObject {
	private Image image;

	public Box(int x, int y, SpriteSheet spriteSheet) {
		super(ObjectIdImpl.BOX, x, y, 2,spriteSheet);
		image = new Image(getImage(2, 1));
	}

	public void internalUpdate(double passedTime) {

	}

	@Override
	public void render(Graphics2D g, Renderer renderer) {
		renderer.draw(image, x, y);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

	public Rectangle getBlockBounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
