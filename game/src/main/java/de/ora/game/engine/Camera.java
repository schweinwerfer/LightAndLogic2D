package de.ora.game.engine;

public class Camera {
	private float x;
	private float y;
	private GameObject gameObject;

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void tick() {
		if(gameObject == null) return;
		x += (gameObject.getX() - x - GameContainer.WIDTH / 2) * 0.05f;
		y += (gameObject.getY() - y - GameContainer.HEIGHT / 2) * 0.05f;

//		if(x <= 0) x = 0;
//		if(x >= Game.WIDTH) x = Game.WIDTH + 32;
//
//		if(y <= 0) y = 0;
//		if(y >= Game.HEIGHT) y = Game.HEIGHT + 32;
	}

	public void follow(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
