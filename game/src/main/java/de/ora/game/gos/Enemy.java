package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.gfx.RadialLight;
import de.ora.game.ext.Renderer;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {

	private final RadialLight light;
	Random random = new Random();
	int choose = 0;
	int hp = 100;

	public Enemy(int x, int y) {
		super(ObjectIdImpl.ENEMY, x, y);
		light = new RadialLight(64, Color.MAGENTA);
	}

	@Override
	protected void internalTick() {
		collision();
		if(hp <= 0) {
			getHandler().remove(this);
		}

		choose = random.nextInt(10);
		if(choose == 0) {
			velX = random.nextInt(4) + -2;
			velY = random.nextInt(4) + -2;
		}
	}

	@Override
	public void render(Graphics2D g, Renderer renderer) {
		renderer.renderLight(light, x + 16, y + 16);
		g.setColor(Color.MAGENTA);
		g.drawRoundRect(x, y, 32, 32, 2, 2);
		g.drawString(hp + "", x + 2, y + 10);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

	public Rectangle getBoundsBig() {
		return new Rectangle(x - 16, y - 16, 64, 64);
	}

	private void collision() {
		for(GameObject gameObject : getHandler().getGameObjects()) {
			if(ObjectIdImpl.BLOCK == gameObject.id) {
				if(getBoundsBig().intersects(gameObject.getBounds())) {
					x -= (velX * 5);
					y -= (velY * 5);

					velX *= -1;
					velY *= -1;
				}
			}
			else if(ObjectIdImpl.BULLET == gameObject.id) {
				if(getBounds().intersects(gameObject.getBounds())) {
					Bullet bullet = (Bullet) gameObject;
					int damage = bullet.getDamage();
					this.hp -= damage;
					if(!bullet.isPenetrates()) {
						getHandler().remove(bullet);
					}
				}
			}
		}
	}
}
