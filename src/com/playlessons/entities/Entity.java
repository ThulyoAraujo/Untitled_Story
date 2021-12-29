package com.playlessons.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.playlessons.main.Game;
import com.playlessons.world.Camera;

public class Entity {

	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getsprite(6 * 16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getsprite(7 * 16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getsprite(7 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_BAT = Game.spritesheet.getsprite(4 * 16, 6 * 16, 16, 16);
	// public static BufferedImage ENEMY_EN2 = Game.spritesheet.getsprite(8 * 16,
	// 16, 16, 16);
	// public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getsprite(9 *
	// 16, 16, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getsprite(8 * 16, 0, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getsprite(9 * 16, 0, 16, 16);
	public static BufferedImage GUN_UP = Game.spritesheet.getsprite(8 * 16, 16, 16, 16);
	public static BufferedImage GUN_DOWN = Game.spritesheet.getsprite(9 * 16, 16, 16, 16);
	public static BufferedImage NPC_GENERAL = Game.spritesheet.getsprite(6 * 16, 6 * 16, 16, 16);

	public static BufferedImage HEARTEMOJI = Game.spritesheet.getsprite(2 * 16, 3 * 16, 16, 16);
	public static BufferedImage FRAMEWHITELEFT = Game.spritesheet.getsprite(3 * 16, 3 * 16, 16, 16);
	public static BufferedImage FRAMEPNG = Game.spritesheet.getsprite(4 * 16, 3 * 16, 16, 16);
	public static BufferedImage FRAMEWHITERIGHT = Game.spritesheet.getsprite(5 * 16, 3 * 16, 16, 16);
	
	protected double x, y;

	protected int width, height;

	private BufferedImage sprite;

	private int maskx, masky, mwidth, mheight;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}

	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void tick() {
		
	}

	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);

		if (e1Mask.intersects(e2Mask)) {
			return true;
		}

		return false;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
