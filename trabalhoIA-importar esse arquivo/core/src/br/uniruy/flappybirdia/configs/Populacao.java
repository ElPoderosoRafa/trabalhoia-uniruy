package br.uniruy.flappybirdia.configs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;

import br.uniruy.flappybirdia.entidades.Interface;
import br.uniruy.flappybirdia.entidades.Passaro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Populacao implements Interface {

    private List<Passaro> passaros;
    private final int tamanho;
    private int geracao = 0;
    private Texture corpoPassaro;

    public Populacao(int tamanho) {
        this.tamanho = tamanho;
        this.corpoPassaro = new Texture("images/bird.png");

        this.reset();
    }

    @Override
    public void dispose() {
        this.passaros.forEach(Interface::dispose);
    }

    @Override
    public void draw(Batch batch) {
        this.passaros.forEach(e -> e.draw(batch));
    }

    @Override
    public void update(float deltaTime) {
        this.passaros.forEach(e -> e.update(deltaTime));
    }

    public void evolve() {
        this.geracao++;

        List<Passaro> passarosMaiorPlacar = this.passaros.stream()
                .sorted(Comparator.comparing(Passaro::getPlacar).reversed())
                .limit((int) (this.tamanho * 0.1f))
                .collect(Collectors.toList());

        int tamanho = this.tamanho;
        this.passaros = new ArrayList<>(tamanho);

        for (int i = 0; i < passarosMaiorPlacar.size() * 7.5f; i++, tamanho--) {
            int randomIndex = MathUtils.random(0, passarosMaiorPlacar.size() - 1);
            Passaro randomPassaroMaiorPlacar = passarosMaiorPlacar.get(randomIndex);

            Passaro passaro = randomPassaroMaiorPlacar == null
                    ? new Passaro(this.corpoPassaro)
                    : new Passaro(this.corpoPassaro, randomPassaroMaiorPlacar.getCerebro());

            this.passaros.add(passaro);
        }

        while (tamanho-- > 0) {
            this.passaros.add(new Passaro(this.corpoPassaro));
        }
    }

    public void reset() {
        this.geracao = 0;
        this.passaros = new ArrayList<>(this.tamanho);

        int tamanho = this.tamanho;
        while (tamanho-- > 0) {
            Passaro passaro = new Passaro(this.corpoPassaro);

            this.passaros.add(passaro);
        }
    }

    public int getTamanho() { //getSize
        return (int) this.passaros.stream()
                .filter(b -> !b.morto())
                .count();
    }

    public int getGeracao() {
        return this.geracao;
    }

    public int getVivos() {
        return (int) this.passaros.stream()
                .filter(b -> !b.morto())
                .count();
    }

    public float getMaiorPlacar() {
        return this.passaros.stream()
                .filter(b -> !b.morto())
                .max(Comparator.comparing(Passaro::getPlacar))
                .map(Passaro::getPlacar)
                .orElse(0f);
    }
}
