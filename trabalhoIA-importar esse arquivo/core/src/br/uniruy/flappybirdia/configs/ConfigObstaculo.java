package br.uniruy.flappybirdia.configs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;

import br.uniruy.flappybirdia.entidades.Interface;
import br.uniruy.flappybirdia.entidades.Obstaculo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfigObstaculo implements Interface {

    private List<Obstaculo> obstaculos = new ArrayList<>();
    private int qtdObstaculos = 3;

    private float tempoMinObstaculos = 2f;
    private float tempoMaxObstaculos = 3f;
    private float tempoMinProximoObstaculo = 0f;

    private float tempoDecorrido = 0f;

    public ConfigObstaculo() {
        this.criarObstaculo();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void draw(Batch batch) {
        this.obstaculos.forEach(o -> o.draw(batch));
    }

    @Override
    public void update(float deltaTime) {
        this.tempoDecorrido += deltaTime;

        this.obstaculos.forEach(o -> o.update(deltaTime));

        for (int i = this.obstaculos.size() - 1; i >= 0; i--) {
            if (this.obstaculos.get(i).morto()) {
                this.obstaculos.remove(i);
            }
        }

        if (this.obstaculos.size() < this.qtdObstaculos) {
            this.criarObstaculo();
        }
    }

    public void reset() {
        this.obstaculos.clear();
        this.tempoMinProximoObstaculo = 0f;
        this.criarObstaculo();
    }

    private void criarObstaculo() {
        if (this.tempoDecorrido < tempoMinProximoObstaculo) {
            return;
        }

        this.obstaculos.add(new Obstaculo());
        this.tempoMinProximoObstaculo = this.tempoDecorrido + MathUtils.random(this.tempoMinObstaculos, this.tempoMaxObstaculos);
    }

    public List<Obstaculo> getObstaculos() {
        return this.obstaculos;
    }

    public Obstaculo getProximoObstaculo(float minX) throws NoSuchElementException {
        return this.obstaculos.stream()
                .filter(o -> o.getRetanguloCanoBaixo().x + o.getRetanguloCanoBaixo().width > minX)
                .min(Comparator.comparing(o -> o.getRetanguloCanoBaixo().x))
                .orElseThrow(NoSuchElementException::new);
    }
}
