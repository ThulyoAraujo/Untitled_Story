package com.playlessons.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.playlessons.entities.NpcGeneral;
import com.playlessons.entities.Player;
import com.playlessons.main.Game;
import com.playlessons.world.Camera;

//User interface
public class UI {

	public void render(Graphics g) {

		// Plano de fundo (retângulo) do hud
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, 32);
		// Renderiza a vida do player
		g.setColor(Color.red);
		g.fillRect(8, 5, 200, 22);
		g.setColor(Color.green);
		g.fillRect(8, 5, (int) ((Game.player.life / Game.player.maxLife) * 200), 22);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString((int) Game.player.life + "/" + (int) Game.player.maxLife, 70, 23);
		// Moedas exibidas na tela
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.yellow);
		g.drawString("Moedas: " + Game.player.money, 440, 23);
		// Munição exibida na tela
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.black);
		g.drawString("Munição: " + Game.player.ammo, 580, 23);
		// Nome do desenvolvedor
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.setColor(Color.white);
		g.drawString("Desenvolvido por THÚLYO ARAUJO", Game.WIDTH * Game.SCALE - 260, Game.HEIGHT * Game.SCALE - 10);
		if (Player.saveGameChallenge) {
			// Desenha no hud o desafio do jogador
			g.drawImage(NpcGeneral.frameWhiteLeft, 0, Game.HEIGHT * Game.SCALE - 45, 16 * Game.SCALE, 16 * Game.SCALE,
					null);
			g.drawImage(NpcGeneral.framePng, 16 * Game.SCALE, Game.HEIGHT * Game.SCALE - 45, 16 * Game.SCALE,
					16 * Game.SCALE, null);
			g.drawImage(NpcGeneral.enemyBat, 16 * Game.SCALE, Game.HEIGHT * Game.SCALE - 45, 16 * Game.SCALE,
					16 * Game.SCALE, null);
			g.setColor(Color.black);
			g.setFont(new Font("arial", Font.CENTER_BASELINE, 36));
			g.drawString(Integer.toString(NpcGeneral.numberOfMonsterToKill), 16, Game.HEIGHT * Game.SCALE - 7);
			g.drawImage(NpcGeneral.frameWhiteRight, 2 * 16 * Game.SCALE, Game.HEIGHT * Game.SCALE - 45, 16 * Game.SCALE,
					16 * Game.SCALE, null);
			g.drawString(NpcGeneral.classOfTheMonster, 2 * 16 * Game.SCALE + 10, Game.HEIGHT * Game.SCALE - 7);
		}
	}

}
