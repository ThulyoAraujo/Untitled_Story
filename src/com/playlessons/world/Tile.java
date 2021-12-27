package com.playlessons.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.playlessons.main.Game;

public class Tile {

	// Imagens de chão, parede e mapa no geral
	public static final BufferedImage TILE_FLOORGRASS1 = Game.spritesheet.getsprite(0, 0, 16, 16);
	public static final BufferedImage TILE_FLOORGRASS2 = Game.spritesheet.getsprite(16, 0, 16, 16);
	public static final BufferedImage TILE_FLOORCITY = Game.spritesheet.getsprite(2 * 16, 16, 16, 16);
	public static final BufferedImage TILE_FLOORNEXTMAP = Game.spritesheet.getsprite(3 * 16, 16, 16, 16);

	public static final BufferedImage TILE_WALL1 = Game.spritesheet.getsprite(2 * 16, 0, 16, 16);
	public static final BufferedImage TILE_WALL2 = Game.spritesheet.getsprite(3 * 16, 0, 16, 16);
	public static final BufferedImage TILE_WALL3 = Game.spritesheet.getsprite(4 * 16, 0, 16, 16);
	public static final BufferedImage TILE_WALL4 = Game.spritesheet.getsprite(5 * 16, 0, 16, 16);

	public static final BufferedImage TILE_TREE1 = Game.spritesheet.getsprite(0, 3 * 16, 16, 16);
	public static final BufferedImage TILE_TREE2 = Game.spritesheet.getsprite(16, 3 * 16, 16, 16);

	public static final BufferedImage TILE_FENCE1 = Game.spritesheet.getsprite(5 * 16, 16, 16, 16);
	public static final BufferedImage TILE_FENCE2 = Game.spritesheet.getsprite(4 * 16, 2 * 16, 16, 16);
	public static final BufferedImage TILE_FENCE3 = Game.spritesheet.getsprite(4 * 16, 16, 16, 16);
	public static final BufferedImage TILE_FENCE4 = Game.spritesheet.getsprite(6 * 16, 16, 16, 16);
	public static final BufferedImage TILE_FENCE5 = Game.spritesheet.getsprite(6 * 16, 2 * 16, 16, 16);

	public static final BufferedImage TILE_WATER = Game.spritesheet.getsprite(0, 4 * 16, 16, 16);

	public static final BufferedImage TILE_HOUSEROOFLEFT1 = Game.spritesheet.getsprite(26 * 16, 0, 16, 16);
	public static final BufferedImage TILE_HOUSEROOFRIGHT1 = Game.spritesheet.getsprite(29 * 16, 0, 16, 16);
	public static final BufferedImage TILE_HOUSEROOFMIDDLE1 = Game.spritesheet.getsprite(27 * 16, 0, 16, 16);
	public static final BufferedImage TILE_HOUSEDOWNLEFT1 = Game.spritesheet.getsprite(26 * 16, 16, 16, 16);
	public static final BufferedImage TILE_HOUSEDOWNRIGHT1 = Game.spritesheet.getsprite(29 * 16, 16, 16, 16);
	public static final BufferedImage TILE_HOUSEDOOR1 = Game.spritesheet.getsprite(27 * 16, 16, 16, 16);
	public static final BufferedImage TILE_HOUSEWINDOW1 = Game.spritesheet.getsprite(25 * 16, 0, 16, 16);

	public static final BufferedImage TILE_BUILDINGDOWNLEFT = Game.spritesheet.getsprite(26 * 16, 5 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGDOWNRIGHT = Game.spritesheet.getsprite(29 * 16, 5 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGDOWNBOARD = Game.spritesheet.getsprite(28 * 16, 3 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGDOOR = Game.spritesheet.getsprite(28 * 16, 5 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGSIDELEFT = Game.spritesheet.getsprite(26 * 16, 4 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGSIDERIGHT = Game.spritesheet.getsprite(29 * 16, 4 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGBLOCKGENERAL = Game.spritesheet.getsprite(28 * 16, 4 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGWINDOW = Game.spritesheet.getsprite(25 * 16, 5 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGROOFDOWNLEFT = Game.spritesheet.getsprite(26 * 16, 3 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGROOFTOPLEFT = Game.spritesheet.getsprite(26 * 16, 2 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGROOFMIDDLEDOWN = Game.spritesheet.getsprite(27 * 16, 3 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGROOFMIDDLETOP = Game.spritesheet.getsprite(27 * 16, 2 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGROOFDOWNRIGHT = Game.spritesheet.getsprite(29 * 16, 3 * 16, 16, 16);
	public static final BufferedImage TILE_BUILDINGROOFTOPRIGHT = Game.spritesheet.getsprite(29 * 16, 2 * 16, 16, 16);

	public static final BufferedImage TILE_BUILDINGHOSPITALDOOR = Game.spritesheet.getsprite(28 * 16, 8 * 16, 16, 16);
	public static final BufferedImage TILE_FLOORINFRONTOFHELPER = Game.spritesheet.getsprite(16, 4 * 16, 16, 16);

	public static final BufferedImage TILE_DESKMARKETLEFT = Game.spritesheet.getsprite(24 * 16, 3 * 16, 16, 16);
	public static final BufferedImage TILE_DESKMARKETRIGHT = Game.spritesheet.getsprite(25 * 16, 3 * 16, 16, 16);
	public static final BufferedImage TILE_DESKHELPERLEFT = Game.spritesheet.getsprite(24 * 16, 7 * 16, 16, 16);
	public static final BufferedImage TILE_DESKHELPERRIGHT = Game.spritesheet.getsprite(25 * 16, 7 * 16, 16, 16);
	public static final BufferedImage TILE_DESKQUESTLEFT = Game.spritesheet.getsprite(24 * 16, 11 * 16, 16, 16);
	public static final BufferedImage TILE_DESKQUESTRIGHT = Game.spritesheet.getsprite(25 * 16, 11 * 16, 16, 16);

	private BufferedImage sprite;
	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
