package de.ora.game.gos;

import de.ora.game.engine.ObjectId;

import java.awt.*;

public enum ObjectIdImpl implements ObjectId {
	PLAYER(Color.WHITE),
	BLOCK(new Color(127, 127, 127)),
	BULLET(null),
	ENEMY(new Color(237, 28, 36)),
	BOX(new Color(185, 122, 87));

	private Color mapCode;

	ObjectIdImpl(Color mapCode) {
		this.mapCode = mapCode;
	}

	public boolean matches(Color code) {
		if(mapCode == null || code == null) return false;
		return mapCode.equals(code);
	}
}
