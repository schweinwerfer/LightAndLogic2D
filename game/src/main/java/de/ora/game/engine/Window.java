package de.ora.game.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
	private Dimension dimension;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy strategy;
	private Graphics graphics;

	public Window(int width, int height, float scale, String title, GameContainer game) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		dimension = new Dimension((int) (width * scale), (int) (height * scale));

		canvas = new Canvas();
		canvas.setMaximumSize(dimension);
		canvas.setMinimumSize(dimension);
		canvas.setPreferredSize(dimension);
		canvas.addKeyListener(game.getKeyInput());

		JFrame frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		ActionListener escListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				game.exit();
				frame.dispose();
				System.exit(0);
			}
		};
		frame.getRootPane().registerKeyboardAction(escListener,
			KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
			JComponent.WHEN_IN_FOCUSED_WINDOW);

		frame.setVisible(true);

		canvas.createBufferStrategy(3);
		strategy = canvas.getBufferStrategy();
		graphics = strategy.getDrawGraphics();
		canvas.requestFocus();
	}

	public void render() {
		graphics.drawImage(image, 0, 0, dimension.width, dimension.height, null);
		strategy.show();
	}

	public BufferedImage getImage() {
		return image;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Graphics getGraphics() {
		return graphics;
	}
}
