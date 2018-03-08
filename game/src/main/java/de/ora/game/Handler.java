package de.ora.game;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();


	public void render(Graphics g) {
		for(GameObject gameObject : gameObjects) {
			gameObject.render(g);
		}
	}

	public void tick() {
		for(GameObject gameObject : gameObjects) {
			gameObject.tick();
		}
	}

	public void add(GameObject gameObject) {
		gameObject.setHandler(this);
		gameObjects.add(gameObject);
	}

	public boolean remove(GameObject gameObject) {
		return gameObjects.remove(gameObject);
	}

	public Player getPlayer() {
		for(GameObject gameObject : gameObjects) {
			if(ObjectId.PLAYER == gameObject.id) {
				return (Player) gameObject;
			}
		}

		return null;
	}
}
