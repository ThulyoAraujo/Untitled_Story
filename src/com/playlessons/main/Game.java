package com.playlessons.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.playlessons.entities.BulletShoot;
import com.playlessons.entities.EnemyNovo;
import com.playlessons.entities.Entity;
import com.playlessons.entities.NpcGeneral;
import com.playlessons.entities.Player;
import com.playlessons.graficos.Spritesheet;
import com.playlessons.graficos.UI;
import com.playlessons.world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 256; // 240 - 256
	public static final int HEIGHT = 160; // 160
	public static final int SCALE = 3; // 3

	public static int currentLevel = 1, MAX_LEVEL = 2;

	private BufferedImage image;

	public static List<Entity> entities;
	public static List<EnemyNovo> enemies;
	public static List<NpcGeneral> npcs;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;

	public static World world;

	public static Player player;

	public static Random rand;

	public UI ui;

	// Importar fonte
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelFont.ttf");
	public Font newFont;

	public static String gameState = "MENU";
	private boolean showMessageGameOver = false;
	private int framesGameOver = 0;
	private boolean restartGame = false;

	public Menu menu;

	public int mx, my;

	public boolean saveGame = false;
	public boolean saveGameGeneral = false;
	public boolean priority = true;

	public Game() {
		rand = new Random();
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		// Inicializando objetos

		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<EnemyNovo>();
		npcs = new ArrayList<NpcGeneral>();
		bullets = new ArrayList<BulletShoot>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(2 * 16, 2 * 16, 16, 16, spritesheet.getsprite(0, 0, 16, 16));
		entities.add(player);
		world = new World("/level1.png", currentLevel);

		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		menu = new Menu();
	}

	public void initFrame() {
		frame = new JFrame("Untitled Story");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		if (gameState == "NORMAL") {
			if (this.currentLevel == Menu.lastLevel) {
				priority = false;
			}

			if (this.saveGame) {

				this.saveGame = false;
				String[] opt1 = { "level", "vida", "XPlayer", "YPlayer" };
				if (Menu.lastLevel != 0 && priority) {
					this.currentLevel = Menu.lastLevel;
				}
				int[] opt2 = { this.currentLevel, (int) player.life, player.getX(), player.getY() };
				Menu.saveGame(opt1, opt2, 12);
				System.out.println("Jogo salvo!");

			} else if (player.saveGameHospital) {
				this.saveGame = false;
				player.saveGameHospital = false;
				String[] opt1 = { "level", "vida", "XPlayer", "YPlayer" };
				int[] opt2 = { Menu.CurrentLevelBackup, (int) player.life, player.getXBackup(), player.getYBackup(),
						player.getLastHospital() };
				Menu.saveGame(opt1, opt2, 12);
				System.out.println("Hospital salvo!");
			}

			this.restartGame = false;
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}

			// PRÓXIMO NÍVEL
			if (player.nextLevel) {
				currentLevel++;
				if (currentLevel > MAX_LEVEL) {
					currentLevel = 1;
				}
				String newWorld = "level" + currentLevel + ".png";
				World.restartGame(newWorld, currentLevel);
				// world = new World(newWorld, currentLevel);

			}
			/*
			 * Reiniciar jogo quando todos os inimigos morrerem else if (enemies.size() ==
			 * 0) { // Avançar para o próximo nível currentLevel++; if (currentLevel >
			 * MAX_LEVEL) { currentLevel = 1; } String newWorld = "level" + currentLevel +
			 * ".png"; World.restartGame(newWorld); }
			 */
		} else if (gameState == "GAME_OVER") {
			// System.out.println("Game over");
			this.framesGameOver++;
			if (this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if (this.showMessageGameOver) {
					this.showMessageGameOver = false;
				} else {
					this.showMessageGameOver = true;
				}
			}

			if (restartGame) {
				this.restartGame = false;
				this.gameState = "NORMAL";
				World.restartingGame = true;
				currentLevel = 1;
				String newWorld = "level" + currentLevel + ".png";
				World.restartGame(newWorld, currentLevel);
			}

			/*
			 * Reiniciar o jogo todo se morrer if (restartGame) { this.restartGame = false;
			 * this.gameState = "NORMAL"; currentLevel = 1; String newWorld = "level" +
			 * currentLevel + ".png"; World.restartGame(newWorld); }
			 */
		} else if (gameState == "MENU") {
			menu.tick();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Renderização do jogo
		// Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		// Para rotacionar o personagem, ângulo, posição + metade do personagem
		// g2.rotate(Math.toRadians(0),90+5,90+5);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
			;
		}

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 32, WIDTH * SCALE, HEIGHT * SCALE - 32, null);
		// Renderiza o hud do player
		ui.render(g);

		if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.white);
			g.drawString("GAME OVER", WIDTH * SCALE / 2 - 100, HEIGHT * SCALE / 2);
			g.setFont(new Font("arial", Font.BOLD, 28));
			if (showMessageGameOver) {
				g.drawString("Pressione enter para reiniciar", WIDTH * SCALE / 2 - 200, HEIGHT * SCALE / 2 + 40);
			}

		} else if (gameState == "MENU") {
			menu.render(g);
		}
		// Rotacionar objetos com o mouse
		/*
		 * Graphics2D g2 = (Graphics2D) g; double angleMouse = Math.atan2(200 + 25 - my,
		 * 200 + 25 - mx); g2.rotate(angleMouse, 200 + 25, 200 + 25);
		 * g.setColor(Color.red); g.fillRect(200, 200, 50, 50);
		 */
		bs.show();
	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			// System.out.println("Meu jogo está rodando!");
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			// System.out.println("Direita");
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			// System.out.println("Esquerda");
			player.left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// System.out.println("Cima");
			player.up = true;

			if (gameState == "MENU") {
				menu.up = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			// System.out.println("Baixo");
			player.down = true;

			if (gameState == "MENU") {
				menu.down = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if (gameState == "MENU") {
				menu.enter = true;
			} else if (gameState == "NORMAL") {
				if(player.heartEmoji) {
				player.gameHospital = true;
				} else if (player.monsterEmoji) {
					player.gameChallenge = true;
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			menu.pause = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_C) {
			if (gameState == "NORMAL") {
				this.saveGame = true;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			// System.out.println("Direita");
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			// System.out.println("Esquerda");
			player.left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// System.out.println("Cima");
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			// System.out.println("Baixo");
			player.down = false;
		}

	}

}
