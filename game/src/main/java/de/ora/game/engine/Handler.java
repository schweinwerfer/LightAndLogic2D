package de.ora.game.engine;

import de.ora.game.ext.Renderer;
import de.ora.game.gos.ObjectIdImpl;
import de.ora.game.gos.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Handler {
	private List<GameObject> gameObjects = new ArrayList<>();
	private Player player;


	public void render(Graphics2D g, Renderer renderer) {
		for(final GameObject gameObject : gameObjects) {
			gameObject.render(g, renderer);
		}
	}

	public void tick() {
		for(final GameObject gameObject : gameObjects) {
			gameObject.tick();
		}
	}

	public void add(GameObject gameObject) {
		gameObject.setHandler(this);
		List<GameObject> copy = new ArrayList<>(gameObjects);
		copy.add(gameObject);
		gameObjects = copy;
		if(ObjectIdImpl.PLAYER == gameObject.id) {
			player = (Player) gameObject;
		}
	}

	public boolean remove(GameObject gameObject) {
		List<GameObject> copy = new ArrayList<>(gameObjects);
		boolean success = copy.remove(gameObject);
		gameObjects = copy;
		return success;
	}

	public Player getPlayer() {
		return player;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
}
