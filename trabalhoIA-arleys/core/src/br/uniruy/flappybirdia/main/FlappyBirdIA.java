package br.uniruy.flappybirdia.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.uniruy.flappybirdia.configs.ConfigObstaculo;
import br.uniruy.flappybirdia.configs.Populacao;

public class FlappyBirdIA extends ApplicationAdapter {
	public static Populacao populacao;
	public static ConfigObstaculo configObstaculo;
	private float larguraDispositivo;
	private float alturaDispositivo;
	private Texture fundo;
	private Texture gameOver;
	private SpriteBatch batch;
	private BitmapFont fonte;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		populacao = new Populacao(100);
		configObstaculo = new ConfigObstaculo();
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		fundo = new Texture("images/fundo.png");
		gameOver = new Texture("images/game_over.png");
		
		fonte = new BitmapFont();
		fonte.setColor(Color.YELLOW);
		fonte.getData().setScale(2);
	}

	@Override
	public void render () { // isso aqui representa o loop
		float deltaTime = Gdx.graphics.getDeltaTime();
		populacao.update(deltaTime);
		configObstaculo.update(deltaTime);

		batch.begin();
		
		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		configObstaculo.draw(batch); // chamando o metodo de desenhar os obstaculos
		populacao.draw(batch); // desenha 100 passaros na tela


		fonte.draw(batch, "Geração: " + populacao.getGeracao(), 450f, alturaDispositivo - 30f);
		fonte.draw(batch, "Vivos: " + populacao.getVivos(), 450f, alturaDispositivo - 70f);

		if(Gdx.input.justTouched()){ // avança de geração se clicar na tela
			populacao.evolve();
			configObstaculo.reset();
		}

		if (populacao.getVivos() == 0) { // Placar de vivos na tela
			batch.draw(gameOver,larguraDispositivo/2 - gameOver.getWidth()/2, alturaDispositivo/2);
			populacao.evolve();
			configObstaculo.reset();
		}
		
		batch.end();
	}
	
	

	@Override
	public void dispose () {
		batch.dispose();
		fundo.dispose();
	}
}
