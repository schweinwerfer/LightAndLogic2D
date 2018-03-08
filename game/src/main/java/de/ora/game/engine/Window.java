package de.ora.game.engine;

import de.ora.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Window {
	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);

		final Dimension dimension = new Dimension(width, height);
		frame.setPreferredSize(dimension);
		frame.setMaximumSize(dimension);
		frame.setMinimumSize(dimension);

		frame.add(game);
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
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
