package br.uniruy.flappybirdia.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BasePassaro implements Interface {
    protected Vector2 posicao = new Vector2();
    protected Vector2 dimensoes = new Vector2(50, 50); // tamanho do passaro
    protected Vector2 aceleracao = new Vector2();

    protected float rotation = 0f;
    protected TextureRegion textureRegion;

    protected boolean habilitarGravidade = false;
    protected float gravidade = 9.81f; // quanto maior, mais rapida a queda
    protected float massa = 1f;

    public BasePassaro(Texture texture) {
        this.textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void dispose() {
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(
                this.textureRegion,
                this.posicao.x,
                this.posicao.y,
                this.dimensoes.x / 2f,
                this.dimensoes.y / 2f,
                this.dimensoes.x,
                this.dimensoes.y,
                1,
                1,
                this.rotation
        );
    }

    @Override
    public void update(float deltaTime) {
        if (this.habilitarGravidade) {
            this.aceleracao.add(Vector2.Y.cpy().scl(-1f * this.gravidade * this.massa * deltaTime));
        }

        this.posicao.add(this.aceleracao.cpy().scl(deltaTime));
    }

    public Vector2 getPosition() {
        return this.posicao;
    }
}
