package br.uniruy.flappybirdia.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;
import java.util.List;

public class Obstaculo implements Interface {
    private float velocidadeColunas = 155f; // velocidade de criaçao das colunas
    private float espacoEntreCanos = 200f; // espaço entre as colunas
    float altura = MathUtils.random(Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getHeight() * 0.5f);
	private float larguraDispositivo = Gdx.graphics.getWidth();
	private float alturaDispositivo = Gdx.graphics.getHeight();
    private Rectangle retanguloCanoBaixo = new Rectangle();
    private Rectangle retanguloCanoTopo = new Rectangle();
	private Texture canoTopo = new Texture("images/cano_topo.png");
	private Texture canoBaixo = new Texture("images/cano_baixo.png");
	
    public Obstaculo() {
    	
        this.retanguloCanoBaixo.x = larguraDispositivo; // cano baixo
        this.retanguloCanoBaixo.y = alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos/2 + altura;
        this.retanguloCanoBaixo.width = canoBaixo.getWidth();
        this.retanguloCanoBaixo.height = canoBaixo.getHeight();

        this.retanguloCanoTopo.x = larguraDispositivo; // cano cima horizontal
        this.retanguloCanoTopo.y = alturaDispositivo /2 + espacoEntreCanos/2 + altura; // cano cima vertical
        this.retanguloCanoTopo.width = canoTopo.getWidth(); // largura do cano de cima
        this.retanguloCanoTopo.height = canoTopo.getHeight(); // altura do cano de cima
    }

    @Override
    public void dispose() {
    }

    @Override
    public void draw(Batch batch) {
    	
    	
   
	   	batch.draw(canoTopo, this.retanguloCanoTopo.x,  this.retanguloCanoTopo.y);
		batch.draw(canoBaixo, this.retanguloCanoBaixo.x, this.retanguloCanoBaixo.y);
		
   
        batch.end();
        

 /*
        ShapeRenderer shape = new ShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(
                this.retanguloCanoBaixo.x,
                this.retanguloCanoBaixo.y,
                this.retanguloCanoBaixo.width,
                this.retanguloCanoBaixo.height
        );
        shape.rect(
                this.retanguloCanoTopo.x,
                this.retanguloCanoTopo.y,
                this.retanguloCanoTopo.width,
                this.retanguloCanoTopo.height
        );
        shape.end();

        shape.dispose();
        */
        batch.begin();
    }

    @Override
    public void update(float deltaTime) {
        this.retanguloCanoBaixo.x -= this.velocidadeColunas * deltaTime;
        this.retanguloCanoTopo.x = this.retanguloCanoBaixo.x;
    }

    public boolean morto() {
        return this.retanguloCanoBaixo.x + this.retanguloCanoBaixo.width < 0f;
    }

    public List<Rectangle> getRectangles() {
        return Arrays.asList(this.retanguloCanoTopo, this.retanguloCanoBaixo);
    }

    public Rectangle getRetanguloCanoTopo() {
        return this.retanguloCanoTopo;
    }

    public Rectangle getRetanguloCanoBaixo() {
        return this.retanguloCanoBaixo;
    }
}
