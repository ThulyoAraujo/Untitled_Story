package com.playlessons.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.playlessons.entities.Player;
import com.playlessons.main.Game;

//User interface
public class UI {

	public void render(Graphics g) {

		// Plano de fundo (retângulo) do hud
		g.setColor(Color.white);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, 32);
		// Renderiza a vida do player
		g.setColor(Color.red);
		g.fillRect(8, 2, 200, 22);
		g.setColor(Color.green);
		g.fillRect(8, 2, (int) ((Game.player.life / Game.player.maxLife) * 200), 22);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString((int) Game.player.life + "/" + (int) Game.player.maxLife, 70, 20);
		// Moedas exibidas na tela
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.yellow);
		g.drawString("Moedas: " + Game.player.money, 440, 20);
		// Munição exibida na tela
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.black);
		g.drawString("Munição: " + Game.player.ammo, 580, 20);
		// Nome do desenvolvedor
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.setColor(Color.white);
		g.drawString("Desenvolvido por THÚLYO ARAUJO", Game.WIDTH * Game.SCALE - 260, Game.HEIGHT * Game.SCALE - 10);

	}

}
