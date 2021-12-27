package com.playlessons.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;

import javax.imageio.ImageIO;

import com.playlessons.entities.*;
import com.playlessons.graficos.Spritesheet;
import com.playlessons.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public static boolean restartingGame = false;

	public World(String path, int currentLevel) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();

			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

			// Gerando mapa e adicionando objetos nele
			for (int xx = 0; xx < map.getWidth(); xx++) {

				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					if (currentLevel == 1) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOORCITY);
					} else {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOORGRASS2);
					}
					if (pixelAtual == 0xFF000000) {
						// Floor com muitas folhas
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOORGRASS2);
					} else if (pixelAtual == 0xFFDAFF7F) {
						// Chão claro de cidade
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOORCITY);
					} else if (pixelAtual == 0xFFFF7FB6) {
						// Chão para o próximo mapa
						tiles[xx + (yy * WIDTH)] = new EspecialFoorTile(xx * 16, yy * 16, Tile.TILE_FLOORNEXTMAP);
					}

					else if (pixelAtual == 0xFFFFFFFF) {
						// Parede fim 1
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL1);
					} else if (pixelAtual == 0xFF808080) {
						// Parede vertical
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL2);
					} else if (pixelAtual == 0xFF303030) {
						// Parede fim 2
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL3);
					} else if (pixelAtual == 0xFF404040) {
						// Parede horizontal
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL4);
					}

					else if (pixelAtual == 0xFF7F0000) {
						// CASA JANELA 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEWINDOW1);
					} else if (pixelAtual == 0xFF7C0303) {
						// CASA TETO MEIO 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEROOFMIDDLE1);
					} else if (pixelAtual == 0xFF7A0909) {
						// CASA TETO ESQUERDA 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEROOFLEFT1);
					} else if (pixelAtual == 0xFF770B0B) {
						// CASA TETO DIREITA 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEROOFRIGHT1);
					} else if (pixelAtual == 0xFF750E0E) {
						// CASA LATERAL ESQUERDA 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEDOWNLEFT1);
					} else if (pixelAtual == 0xFF750C0C) {
						// CASA LATERAL DIREITA 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEDOWNRIGHT1);
					} else if (pixelAtual == 0xFF721313) {
						// CASA PORTA 1
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_HOUSEDOOR1);
					}

					else if (pixelAtual == 0xFF4800FF) {
						// PRÉDIO LOJA LATERAL ESQUERDA BAIXA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGDOWNLEFT);
					} else if (pixelAtual == 0xFF4800DD) {
						// PRÉDIO LOJA LATERAL DIREITA BAIXA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGDOWNRIGHT);
					} else if (pixelAtual == 0xFF4800E8) {
						// PRÉDIO LOJA PLACA BAIXA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGDOWNBOARD);
					} else if (pixelAtual == 0xFF4800F0) {
						// PRÉDIO PORTA GERAL
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGDOOR);
					} else if (pixelAtual == 0xFF4800CD) {
						// PRÉDIO LATERAL ESQUERDA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGSIDELEFT);
					} else if (pixelAtual == 0xFF4800C2) {
						// PRÉDIO LATERAL DIREITA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGSIDERIGHT);
					} else if (pixelAtual == 0xFF4800C9) {
						// PRÉDIO BLOCO GERAL
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGBLOCKGENERAL);
					} else if (pixelAtual == 0xFF4800C6) {
						// PRÉDIO JANELA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGWINDOW);
					} else if (pixelAtual == 0xFF4800BE) {
						// PRÉDIO TELHADO LATERAL BAIXO ESQUERDA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGROOFDOWNLEFT);
					} else if (pixelAtual == 0xFF4800A7) {
						// PRÉDIO TELHADO LATERAL ALTO ESQUERDA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGROOFTOPLEFT);
					} else if (pixelAtual == 0xFF4800BA) {
						// PRÉDIO TELHADO MEIO BAIXO
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGROOFMIDDLEDOWN);
					} else if (pixelAtual == 0xFF4800A3) {
						// PRÉDIO TELHADO MEIO ALTO
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGROOFMIDDLETOP);
					} else if (pixelAtual == 0xFF4800AE) {
						// PRÉDIO LATERAL BAIXO DIREITA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGROOFDOWNRIGHT);
					} else if (pixelAtual == 0xFF480097) {
						// PRÉDIO LOJA LATERAL ALTO DIREITA
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_BUILDINGROOFTOPRIGHT);
					}

					else if (pixelAtual == 0xFF4CFF00) {
						// Árvore
						tiles[xx + (yy * WIDTH)] = new TreeTile(xx * 16, yy * 16, Tile.TILE_TREE1);
					} else if (pixelAtual == 0xFF5AFF14) {
						// Árvore pinheiro
						tiles[xx + (yy * WIDTH)] = new TreeTile(xx * 16, yy * 16, Tile.TILE_TREE2);
					}

					else if (pixelAtual == 0xFFA5FF7F) {
						// Cerca horizontal
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_FENCE1);
					} else if (pixelAtual == 0xFFA5FF8F) {
						// Cerca vertical esquerda
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_FENCE2);
					} else if (pixelAtual == 0xFFAFFF9B) {
						// Cerca vertical direita
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_FENCE5);
					} else if (pixelAtual == 0xFFA1FF87) {
						// Cerca superior esquerda
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_FENCE3);
					} else if (pixelAtual == 0xFF9DFF84) {
						// Cerca superior direita
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_FENCE4);
					}

					else if (pixelAtual == 0xFF0094FF) {
						// Água geral
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_WATER);
					}

					else if (pixelAtual == 0xFF7C3607) {
						// MESA LATERAL ESQUERDA DO MERCADOR
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_DESKMARKETLEFT);
					} else if (pixelAtual == 0xFF7C3A0D) {
						// MESA LATERAL DIREITA DO MERCADOR
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_DESKMARKETRIGHT);
					} else if (pixelAtual == 0xFF7F3300) {
						// MESA LATERAL ESQUERDA DO CURANDEIRO
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_DESKHELPERLEFT);
					} else if (pixelAtual == 0xFF7C3403) {
						// MESA LATERAL DIREITA DO CURANDEIRO
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_DESKHELPERRIGHT);
					} else if (pixelAtual == 0xFF7C3C11) {
						// MESA LATERAL ESQUERDA DOS DESAFIOS
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_DESKQUESTLEFT);
					} else if (pixelAtual == 0xFF7C3D12) {
						// MESA LATERAL DIREITA DOS DESAFIOS
						tiles[xx + (yy * WIDTH)] = new ObjectTile(xx * 16, yy * 16, Tile.TILE_DESKQUESTRIGHT);
					}

					else if (pixelAtual == 0xFFFF006E) {
						if (restartingGame == true) {
							// Player NO HOSPITAL
							Game.player.setX(xx * 16);
							Game.player.setY(yy * 16);
							restartingGame = false;
						}
						// CHÃO NA FRENTE DA CURANDEIRA
						tiles[xx + (yy * WIDTH)] = new HospitalTile(xx * 16, yy * 16, Tile.TILE_FLOORINFRONTOFHELPER);
					} else if (pixelAtual == 0xFF0026FF && restartingGame == false) {
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					} else if (pixelAtual == 0xFFFF00E5) {
						// Enemy
						NpcGeneral npc = new NpcGeneral(xx * 16, yy * 16, 16, 16, Entity.NPC_GENERAL);
						Game.entities.add(npc);
						Game.npcs.add(npc);

					} else if (pixelAtual == 0xFFFF0000) {
						// Enemy
						EnemyNovo en = new EnemyNovo(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					} else if (pixelAtual == 0xFFFF6A00) {
						// Weapon
						Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
					} else if (pixelAtual == 0xFFFF00DC) {
						// Life pack
						Game.entities.add(new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN));
					} else if (pixelAtual == 0xFFFFD800) {
						// Bullet
						Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN));
					}
				}
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + width - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height - 1) / TILE_SIZE;

		int x4 = (xnext + width - 1) / TILE_SIZE;
		int y4 = (ynext + height - 1) / TILE_SIZE;

		if (!((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x1 + (y1 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x1 + (y1 * World.WIDTH)] instanceof ObjectTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof ObjectTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof ObjectTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof ObjectTile))) {
			return true;
		}

		return false;
	}

	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		if (!((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x1 + (y1 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof TreeTile)
				|| (tiles[x1 + (y1 * World.WIDTH)] instanceof ObjectTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof ObjectTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof ObjectTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof ObjectTile))) {
			return true;
		}

		return false;
	}

	public static boolean nextMap(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		if (((tiles[x1 + (y1 * World.WIDTH)] instanceof EspecialFoorTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof EspecialFoorTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof EspecialFoorTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof EspecialFoorTile))) {
			return true;
		}

		return false;
	}

	public static boolean newHospital(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		if (((tiles[x1 + (y1 * World.WIDTH)] instanceof HospitalTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof HospitalTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof HospitalTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof HospitalTile))) {
			return true;
		}
		return false;
	}

	public static void restartGame(String level, int currentLevel) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<EnemyNovo>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		//Game.player = new Player(2 * 16, 2 * 16, 16, 16, Game.spritesheet.getsprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/" + level, currentLevel);
		return;
	}

	/*
	 * Reset total no game public static void restartGame(String level) {
	 * Game.entities.clear(); Game.enemies.clear(); Game.entities = new
	 * ArrayList<Entity>(); Game.enemies = new ArrayList<EnemyNovo>();
	 * Game.spritesheet = new Spritesheet("/spritesheet.png"); Game.player = new
	 * Player(0, 0, 16, 16, Game.spritesheet.getsprite(32, 0, 16, 16));
	 * Game.entities.add(Game.player); Game.world = new World("/" + level); return;
	 * }
	 */

	public void render(Graphics g) {

		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;

		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

}
