package br.uniruy.flappybirdia.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import br.uniruy.flappybirdia.main.FlappyBirdIA;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Flappy Bird - Trabalho I.A UNIRUY ");
		new Lwjgl3Application(new FlappyBirdIA(), config);
	}
}
