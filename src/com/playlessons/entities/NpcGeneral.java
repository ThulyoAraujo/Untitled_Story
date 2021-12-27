package com.playlessons.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.playlessons.world.Camera;

public class NpcGeneral extends Entity {

	BufferedImage downNPC;

	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 2;

	public NpcGeneral(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		// Criação das listas de imagens do movimento do NPC
		downNPC = Entity.NPC_GENERAL;

	}

	public void tick() {

	}

	public void render(Graphics g) {
		g.drawImage(downNPC, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
