package com.playlessons.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.playlessons.main.Game;
import com.playlessons.world.Camera;
import com.playlessons.world.World;

public class EnemyNovo extends Entity {

	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	// Direção inicial do inimigo
	public int dir = down_dir;
	private double speed = 0.4;

	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;

	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 2;

	// Criação das listas de imagens do movimento do inimigo
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	private BufferedImage[] upEnemy;
	private BufferedImage[] downEnemy;
	private BufferedImage enemyDamage;

	private int lifeEnemy = 10;

	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public EnemyNovo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);

		// Listas de imagens do jogador
		rightEnemy = new BufferedImage[3];
		leftEnemy = new BufferedImage[3];
		upEnemy = new BufferedImage[3];
		downEnemy = new BufferedImage[3];

		// Dano no inimigo
		enemyDamage = Game.spritesheet.getsprite(16, 5 * 16, 16, 16);
		for (int i = 0; i < 3; i++) {
			rightEnemy[i] = Game.spritesheet.getsprite(48 + (i * 16), 8 * 16, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			leftEnemy[i] = Game.spritesheet.getsprite(48 + (i * 16), 7 * 16, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			upEnemy[i] = Game.spritesheet.getsprite(48 + (i * 16), 9 * 16, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			downEnemy[i] = Game.spritesheet.getsprite(48 + (i * 16), 6 * 16, 16, 16);
		}
	}

	public void tick() {
		/*
		 * maskx = 8; masky = 8; maskw = 5; maskh = 5;
		 */
		if (this.isColiddingWithPlayer() == false) {
			if (Game.rand.nextInt(100) < 50) {
				if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
						&& !isColidding((int) (x + speed), this.getY())) {
					dir = right_dir;
					x += speed;
				} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
						&& !isColidding((int) (x - speed), this.getY())) {
					dir = left_dir;
					x -= speed;
				}

				if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
						&& !isColidding(this.getX(), (int) (y + speed))) {
					dir = down_dir;
					y += speed;
				} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
						&& !isColidding(this.getX(), (int) (y - speed))) {
					dir = up_dir;
					y -= speed;
				}
			}
		} else {
			// Estamos perto do player
			// O que podemos fazer?
			if (Game.rand.nextInt(100) < 10) {
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
		//		if (Game.player.life <= 0) {
					// Game over;
					// System.exit(1);
		//		}
				System.out.println("Vida: " + Game.player.life);
			}
		}
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}

		collidingBullet();

		if (lifeEnemy <= 0) {
			destroySelf();
			return;
		}

		if (isDamaged) {
			this.damageCurrent++;
			if (this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;

			}
		}
	}

	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}

	public void collidingBullet() {
		for (int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if (e instanceof BulletShoot) {
				if (Entity.isColidding(this, e)) {
					isDamaged = true;
					lifeEnemy--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}

	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}

	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			EnemyNovo e = Game.enemies.get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == left_dir) {
				g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == up_dir) {
				g.drawImage(upEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == down_dir) {
				g.drawImage(downEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			g.drawImage(enemyDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
