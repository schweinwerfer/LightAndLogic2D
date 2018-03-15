package de.ora.game;

import de.ora.game.engine.AbstractGame;
import de.ora.game.engine.Camera;
import de.ora.game.engine.GameContainer;
import de.ora.game.engine.gfx.Image;
import de.ora.game.engine.gfx.light.Light;
import de.ora.game.ext.Renderer;

public class GameOne extends AbstractGame {
	private Image image;
	private Camera camera;
	private Light light;

	public GameOne() {
//		image = new Image("images/star.png");

	}

	public static void main(String[] args) {
		GameContainer gameContainer = new GameContainer(new GameOne());
		gameContainer.start();
	}

	@Override
	public void update(GameContainer game, double passedTime) {

	}

	@Override
	public void render(GameContainer game, Renderer renderer) {
		camera = game.getCamera();
		//renderer.draw(image, (int) -camera.getX()+32, (int) -camera.getY()+32);

	}
}
