package com.playlessons.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.playlessons.world.World;

public class Menu {

	public String[] options = { "novo jogo", "carregar jogo", "sair" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;

	public static boolean pause = false;
	public static boolean saveExists = false;
	public static boolean saveGame = false;

	public static int lastLevel = 0;
	public static int CurrentLevelBackup;

	public void tick() {
		File file = new File("save.txt");
		if (file.exists()) {
			saveExists = true;
		} else {
			saveExists = false;
		}

		if (up) {
			up = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if (down) {
			down = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}

		if (enter) {
			enter = false;
			if (options[currentOption] == "novo jogo" || options[currentOption] == "continuar") {
				Game.gameState = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
			} else if (options[currentOption] == "carregar jogo") {
				file = new File("save.txt");
				if (file.exists()) {
					String saver = loadGame(12);
					applySave(saver);
				}
			} else if (options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}

	public static void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
			case "level":
				lastLevel = Integer.parseInt(spl2[1]);
				CurrentLevelBackup = lastLevel;
				Game.currentLevel = lastLevel;
				World.restartGame("level" + spl2[1] + ".png", Integer.parseInt(spl2[1]));
				Game.gameState = "NORMAL";
				pause = false;
				break;
			case "vida":
				Game.player.life = Integer.parseInt(spl2[1]);
				break;
			case "XPlayer":
				int XPlayer = Integer.parseInt(spl2[1]);
				Game.player.setXBackup(XPlayer);
				Game.player.setX(XPlayer);
				break;
			case "YPlayer":
				int YPlayer = Integer.parseInt(spl2[1]);
				Game.player.setYBackup(YPlayer);
				Game.player.setY(YPlayer);
				break;
			case "lastHospital":
				int lastHospital = Integer.parseInt(spl2[1]);
				if (lastHospital == 0) {
					Game.player.setLastHospital(1);
				} else {
					Game.player.setLastHospital(lastHospital);
				}
				break;
			}
		}
	}

	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if (file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while ((singleLine = reader.readLine()) != null) {
						String[] transition = singleLine.split(":");
						char[] valor = transition[1].toCharArray();
						transition[1] = "";
						for (int i = 0; i < valor.length; i++) {
							valor[i] -= encode;
							transition[1] += valor[i];
						}
						line += transition[0];
						line += ":";
						line += transition[1];
						line += "/";
					}
				} catch (IOException e) {
					// TODO: handle exception
				}
			} catch (FileNotFoundException e) {
				// TODO: handle exception
			}
		}

		return line;
	}

	public static void saveGame(String[] valor1, int[] valor2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < valor1.length; i++) {
			String current = valor1[i];
			current += ":";
			char[] value = Integer.toString(valor2[i]).toCharArray();

			for (int j = 0; j < value.length; j++) {
				value[j] += encode;
				current += value[j];
			}
			try {
				write.write(current);
				if (i < valor1.length - 1) {
					write.newLine();
				}
			} catch (IOException e) {

			}

		}
		try {
			write.flush();
			write.close();
		} catch (IOException e) {

		}
	}

	public void render(Graphics g) {
		// g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("UNTITLED STORY", Game.WIDTH * Game.SCALE / 2 - 160, Game.HEIGHT * Game.SCALE / 2 - 100);

		// Opções do menu
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 28));
		if (pause == false) {
			g.drawString("Novo jogo", Game.WIDTH * Game.SCALE / 2 - 100, Game.HEIGHT * Game.SCALE / 2 - 10);
		} else {
			g.drawString("Continuar", Game.WIDTH * Game.SCALE / 2 - 100, Game.HEIGHT * Game.SCALE / 2 - 10);
		}
		g.drawString("Carregar jogo", Game.WIDTH * Game.SCALE / 2 - 100, Game.HEIGHT * Game.SCALE / 2 + 30);
		g.drawString("Sair", Game.WIDTH * Game.SCALE / 2 - 100, Game.HEIGHT * Game.SCALE / 2 + 70);

		if (options[currentOption] == "novo jogo") {
			g.drawString(">", Game.WIDTH * Game.SCALE / 2 - 125, Game.HEIGHT * Game.SCALE / 2 - 10);
		} else if (options[currentOption] == "carregar jogo") {
			g.drawString(">", Game.WIDTH * Game.SCALE / 2 - 125, Game.HEIGHT * Game.SCALE / 2 + 30);
		} else if (options[currentOption] == "sair") {
			g.drawString(">", Game.WIDTH * Game.SCALE / 2 - 125, Game.HEIGHT * Game.SCALE / 2 + 70);
		}
	}

}
