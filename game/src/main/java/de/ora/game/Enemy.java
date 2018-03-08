package de.ora.game;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {

	Random random = new Random();
	int choose = 0;
	int hp = 100;

	public Enemy(int x, int y) {
		super(ObjectId.ENEMY, x, y);
	}

	@Override
	protected void internalTick() {
		collision();
		if(hp <= 0) {
			getHandler().remove(this);
		}

		choose = random.nextInt(10);
		if(choose == 0) {
			velX = random.nextInt(4) + -4;
			velY = random.nextInt(4) + -4;
		}
	}

	@Override
	public void render(Graphics g) {
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
			if(ObjectId.BLOCK == gameObject.id) {
				if(getBoundsBig().intersects(gameObject.getBounds())) {
					x -= (velX * 5);
					y -= (velY * 5);

					velX *= -1;
					velY *= -1;
				}
			}
			else if(ObjectId.BULLET == gameObject.id) {
				if(getBounds().intersects(gameObject.getBounds())) {
					int damage = ((Bullet) gameObject).getDamage();
					this.hp -= damage;
				}
			}
		}
	}
}
