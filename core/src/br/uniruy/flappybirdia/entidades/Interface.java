package br.uniruy.flappybirdia.entidades;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Interface {
    void dispose();
    void draw(Batch batch);
    void update(float deltaTime);
}
