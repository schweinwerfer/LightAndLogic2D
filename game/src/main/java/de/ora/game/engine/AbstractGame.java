package de.ora.game.engine;

import de.ora.game.ext.Renderer;

public abstract class AbstractGame {
	public abstract void update(GameContainer game, double passedTime);

	public abstract void render(GameContainer game, Renderer renderer);
}
