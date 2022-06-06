package br.uniruy.flappybirdia.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import br.uniruy.flappybirdia.main.FlappyBirdIA;
import br.uniruy.flappybirdia.neural.RedeNeural;

public class Passaro extends BasePassaro {

    private float forcaPulo = 400f;
    private boolean morto = false;
    private RedeNeural cerebro;
    private float placar = 0f;

    public Passaro(Texture texture) {
        super(texture);

        this.cerebro = new RedeNeural(this);

        this.init();
    }

    public Passaro(Texture texture, RedeNeural oldBrain) {
        this(texture);

        this.cerebro = new RedeNeural(this, oldBrain);

        this.init();
    }

    private void init() {
        this.habilitarGravidade = true;
        this.massa = 60f;

        this.posicao.x = Gdx.graphics.getWidth() * MathUtils.random(0.2f, 0.3f);
        this.posicao.y = Gdx.graphics.getHeight() * MathUtils.random(0.2f, 0.8f);
    }

    public boolean morto() {
        return this.morto;
    }

    @Override
    public void draw(Batch batch) {
        if (this.morto) {
            return;
        }

        super.draw(batch);
    }

    public RedeNeural getCerebro() {
        return this.cerebro;
    }

    @Override
    public void update(float deltaTime) {
        if (this.morto) {
            return;
        }

        this.placar += deltaTime;

        super.update(deltaTime);

        this.cerebro.update(deltaTime);

        this.checarMorto();
    }

    public void pular() {
    
        this.aceleracao.set(0, 0);
        this.aceleracao.add(Vector2.Y.cpy().scl(this.forcaPulo));
    }

    private void checarMorto() {
        if (this.posicao.y >= Gdx.graphics.getHeight() || this.posicao.y + this.dimensoes.y / 2 <= 0) {
            this.morto = true;
        }

        Rectangle retangulo = new Rectangle(); // o passaro, estrutura de colisao
        retangulo.x = this.posicao.x;
        retangulo.y = this.posicao.y;
        retangulo.width = this.dimensoes.x;
        retangulo.height = this.dimensoes.y;

        for (Obstaculo o : FlappyBirdIA.configObstaculo.getObstaculos()) {
            for (Rectangle retan : o.getRectangles()) {
                if (retan.overlaps(retangulo)) {
                    this.morto = true;
                    return;
                }
            }
        }
    }

    public float getPlacar() {
        return placar;
    }
}
