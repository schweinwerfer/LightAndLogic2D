package de.ora.game;

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
		x += (gameObject.getX() - x - Game.WIDTH / 2) * 0.05f;
		y += (gameObject.getY() - y - Game.HEIGHT / 2) * 0.05f;
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
