package com.playlessons.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.playlessons.main.Game;
import com.playlessons.world.Camera;
import com.playlessons.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;

	// Direção inicial do jogador
	public int dir = down_dir;

	// Velocidade do jogador
	public double speed = 1.2;

	// Variáveis que controlam a velocidade com que o personagem muda de aparência
	// ao andar
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 2;
	private boolean moved = false;

	// Criação das listas de imagens do movimento do jogador
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;

	private BufferedImage playerDamage;

	private boolean hasGun = false;

	public int ammo = 0;
	public static int money = 0;

	public boolean isDamaged = false;
	private int damageFrames = 0;

	public boolean shoot = false, mouseShoot = false;

	public double life = 100, maxLife = 100;
	public int mx, my;

	public static boolean nextLevel = false;
	public int lastHospital = 1;
	public boolean saveGameHospital = false;
	public static boolean gameHospital = false;
	public static boolean heartEmoji = false;

	public static boolean saveGameChallenge = false;
	public static boolean gameChallenge = false;
	public static boolean monsterEmoji = false;

	public int currentLevelBackup;
	public int xBackup;
	public int yBackup;

	public int getCurrentLevelBackup() {
		return currentLevelBackup;
	}

	public void setCurrentLevelBackup(int currentLevelBackup) {
		this.currentLevelBackup = currentLevelBackup;
	}

	public int getXBackup() {
		return xBackup;
	}

	public void setXBackup(int xBackup) {
		this.xBackup = xBackup;
	}

	public int getYBackup() {
		return yBackup;
	}

	public void setYBackup(int ybackup) {
		this.yBackup = ybackup;
	}

	public int getLastHospital() {
		return lastHospital;
	}

	public void setLastHospital(int lastHospital) {
		this.lastHospital = lastHospital;
	}

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		// Listas de imagens do jogador
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];

		// Dano no jogador
		playerDamage = Game.spritesheet.getsprite(0, 5 * 16, 16, 16);
		for (int i = 0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getsprite(i * 16, 8 * 16, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getsprite(i * 16, 7 * 16, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			upPlayer[i] = Game.spritesheet.getsprite(i * 16, 9 * 16, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			downPlayer[i] = Game.spritesheet.getsprite(i * 16, 6 * 16, 16, 16);
		}
	}

	public void tick() {

		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}

		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}

		if (up && World.nextMap(this.getX(), (int) (y - speed))) {
			System.out.println("Vou para o próximo nível");
			nextLevel = true;
		} else if (down && World.nextMap(this.getX(), (int) (y + speed))) {
			System.out.println("Vou para o próximo nível");
			nextLevel = true;
		} else {
			nextLevel = false;
		}

		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		checkCollisionHospital();
		checkCollisionChallenge();

		if (isDamaged) {
			this.damageFrames++;
			if (damageFrames == 8) {
				damageFrames = 0;
				isDamaged = false;
			}
		}

		if (shoot) {
			// Criar bala e atirar!
			shoot = false;
			if (hasGun && ammo > 0) {
				ammo--;

				System.out.println("Atirando!");
				// dx e dy é a localização da bala dentro do player, a direção que ela irá sair
				// px e py são as coordenadas "dentro" do player
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 8;
				if (dir == right_dir) {
					// px é mais a direita do player e dx e dy é a direção que a bala irá seguir
					px = 8;
					dx = 1;
				} else if (dir == left_dir) {
					px = 3;
					dx = -1;
				} else if (dir == up_dir) {
					px = 1;
					dy = -1;
				} else {
					px = 10;
					dy = 1;
				}

				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
			}
		}

		if (life <= 0) {
			// Game over
			life = 0;
			Game.gameState = "GAME_OVER";
		}

		updateCamera();

	}

	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}

	public static void moreMoneyForPlayer() {
		money++;
	}

	public void checkCollisionHospital() {

		if (World.newHospital((int) (x - speed), this.getY())) {
			heartEmoji = true;
			if (gameHospital) {
				System.out.println("Hospital");
				saveGameHospital = true;
				life = 100;
				gameHospital = false;
			}
		} else {
			heartEmoji = false;
			saveGameHospital = false;
		}
	}

	public void checkCollisionChallenge() {

		if (World.challenge((int) (x - speed), this.getY())) {
			monsterEmoji = true;
			if (gameChallenge) {
				saveGameChallenge = true;
				System.out.println("Desafio");
				gameChallenge = false;
			}
		} else {
			monsterEmoji = false;
		}
	}

	public void checkCollisionGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Weapon) {
				if (Entity.isColidding(this, atual)) {
					hasGun = true;
					System.out.println("Pegou a arma");
					Game.entities.remove(atual);
				}
			}
		}
	}

	// Coletar munição
	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Bullet) {
				if (Entity.isColidding(this, atual)) {
					ammo += 30;
					System.out.println("Municao atual: " + ammo);
					Game.entities.remove(atual);
				}
			}
		}
	}

	// Coletar vida
	public void checkCollisionLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof LifePack) {
				if (Entity.isColidding(this, atual)) {
					life += 10;
					if (life > 100) {
						life = 100;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (hasGun) {
					// Desenhar arma para direita.
					g.drawImage(Entity.GUN_RIGHT, this.getX() + 7 - Camera.x, this.getY() - Camera.y, null);
				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (hasGun) {
					// Desenhar arma para esquerda.
					g.drawImage(Entity.GUN_LEFT, this.getX() - 8 - Camera.x, this.getY() - Camera.y, null);
				}
			} else if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (hasGun) {
					// Desenhar arma para cima
					g.drawImage(Entity.GUN_UP, this.getX() - 3 - Camera.x, this.getY() - 3 - Camera.y, null);
				}
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (hasGun) {
					// Desenhar arma para baixo
					g.drawImage(Entity.GUN_DOWN, this.getX() + 2 - Camera.x, this.getY() + 7 - Camera.y, null);
				}
			}

		} else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		// VERIFICA SE O PLAYER ESTÁ PULANDO, A LÓGICA SERVE PARA VESTIR SKIN
		/*
		 * if (isJumping) { g.setColor(Color.black); g.fillOval(this.getX() - Camera.x +
		 * 3, this.getY() - Camera.y + 8, 8, 8); }
		 */
	}

}
