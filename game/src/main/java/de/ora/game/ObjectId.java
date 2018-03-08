package de.ora.game;

import java.awt.*;

public enum ObjectId {
	PLAYER(Color.WHITE),
	BLOCK(new Color(127, 127, 127)),
	BULLET(null),
	ENEMY(Color.RED),
	BOX(new Color(185, 122, 87));

	private Color mapCode;

	ObjectId(Color mapCode) {
		this.mapCode = mapCode;
	}

	public boolean matches(Color code) {
		if(mapCode == null || code == null) return false;
		return mapCode.equals(code);
	}
}
