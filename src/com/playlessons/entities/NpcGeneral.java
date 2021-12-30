package com.playlessons.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.playlessons.main.Game;
import com.playlessons.world.Camera;
import com.playlessons.world.ChallengeTile;
import com.playlessons.world.World;

public class NpcGeneral extends Entity {

	public static Random random;

	public static BufferedImage downNPC;
	public static BufferedImage heartEmoji;
	public static BufferedImage frameWhiteLeft;
	public static BufferedImage framePng;
	public static BufferedImage frameWhiteRight;
	public static BufferedImage enemyBat;

	boolean nurse = false;
	boolean challenger = false;
	public static int numberOfMonsterToKill;
	public static String classOfTheMonster;

	public NpcGeneral(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		// Criação das listas de imagens do movimento do NPC
		downNPC = Entity.NPC_GENERAL;
		heartEmoji = Entity.HEARTEMOJI;
		frameWhiteLeft = Entity.FRAMEWHITELEFT;
		framePng = Entity.FRAMEPNG;
		frameWhiteRight = Entity.FRAMEWHITERIGHT;
		enemyBat = Entity.ENEMY_BAT;

	}

	public void tick() {

		if (World.newHospital(getX(), (int) (y + 17))) {
			nurse = true;
		} else {
			nurse = false;
		}

		if (World.challenge(getX(), (int) (y + 17))) {
			challenger = true;
			numberOfMonsterToKill = 5;
			classOfTheMonster = "B";
		} else {
			challenger = false;
		}
	}

	public static void numberOfMonsterToKill() {
		numberOfMonsterToKill--;
		if (numberOfMonsterToKill <= 0) {
			Player.nextLevel = true;
			Player.money += 10;
			Player.saveGameChallenge = false;
		}
	}

	public void render(Graphics g) {
		g.drawImage(downNPC, this.getX() - Camera.x, this.getY() - Camera.y, null);
		if (Player.heartEmoji && nurse) {
			g.drawImage(heartEmoji, this.getX() - Camera.x, this.getY() - Camera.y - 16, null);

		} else if (Player.monsterEmoji && challenger) {
			g.drawImage(frameWhiteLeft, this.getX() - Camera.x - 16, this.getY() - Camera.y - 16, null);
			g.drawImage(framePng, this.getX() - Camera.x, this.getY() - Camera.y - 16, null);
			g.drawImage(enemyBat, this.getX() - Camera.x, this.getY() - Camera.y - 16, null);
			g.drawString(Integer.toString(numberOfMonsterToKill), this.getX() - Camera.x - 10,
					this.getY() - Camera.y - 4);
			g.drawImage(frameWhiteRight, this.getX() - Camera.x + 16, this.getY() - Camera.y - 16, null);
			g.drawString(classOfTheMonster, this.getX() - Camera.x + 19, this.getY() - Camera.y - 4);
		}
	}

}
