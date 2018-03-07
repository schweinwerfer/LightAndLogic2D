package de.ora.game;

import javax.swing.*;
import java.awt.*;

public class Window {
	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);

		final Dimension dimension = new Dimension(width, height);
		frame.setPreferredSize(dimension);
		frame.setMaximumSize(dimension);
		frame.setMinimumSize(dimension);

		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
