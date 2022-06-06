package br.uniruy.flappybirdia.neural;

import com.badlogic.gdx.Gdx;

import br.uniruy.flappybirdia.entidades.Obstaculo;
import br.uniruy.flappybirdia.entidades.Passaro;
import br.uniruy.flappybirdia.main.FlappyBirdIA;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.NormalDistribution;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.weightnoise.WeightNoise;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.transferlearning.FineTuneConfiguration;
import org.deeplearning4j.nn.transferlearning.TransferLearning;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;

import java.util.List;

public class RedeNeural {

    public static MultiLayerNetwork createDefaultNetwork() {
        MultiLayerConfiguration configuracao = new NeuralNetConfiguration.Builder()
                .updater(new Sgd(0.05f))
                .weightInit(WeightInit.RELU)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(4)
                        .nOut(3)
                        .activation(Activation.SIGMOID)
                        .build()
                )
                .layer(1, new DenseLayer.Builder()
                        .nIn(3)
                        .nOut(1)
                        .activation(Activation.SIGMOID)
                        .build())
                .build();

        return new MultiLayerNetwork(configuracao);
    }

    private final Passaro passaro;
    private final MultiLayerNetwork rede;

    public RedeNeural(Passaro passaro) {
        this.passaro = passaro;
        this.rede = createDefaultNetwork();
    }

    public RedeNeural(Passaro passaro, RedeNeural cerebroVelho) {
        this(passaro, cerebroVelho.rede);
    }

    public RedeNeural(Passaro passaro, MultiLayerNetwork redeOriginal) {
        this.passaro = passaro;
        this.rede = new TransferLearning.Builder(redeOriginal)
                .fineTuneConfiguration(new FineTuneConfiguration.Builder()
                        .weightNoise(new WeightNoise(new NormalDistribution(0.01d, 0.005d), true))
                        .build()
                )
                .build();
    }

    private void imaginar() {
        Obstaculo proximo = FlappyBirdIA.configObstaculo.getProximoObstaculo(this.passaro.getPosition().x);

        float distanciaHorizontal = proximo.getRetanguloCanoBaixo().x;
        float distanciaVerticalBaixo = this.passaro.getPosition().y - proximo.getRetanguloCanoBaixo().height;
        float distanciaVerticalTopo = this.passaro.getPosition().y - proximo.getRetanguloCanoTopo().y;

        float[][] entrada = new float[][] {
                {
                        this.normalize(this.passaro.getPosition().y, -Gdx.graphics.getHeight(), Gdx.graphics.getHeight()),
                        this.normalize(distanciaHorizontal, -Gdx.graphics.getWidth(), Gdx.graphics.getWidth()),
                        this.normalize(distanciaVerticalBaixo, -Gdx.graphics.getHeight(), Gdx.graphics.getHeight()),
                        this.normalize(distanciaVerticalTopo, -Gdx.graphics.getHeight(), Gdx.graphics.getHeight()),
                }
        };

        INDArray dados = Nd4j.create(entrada);

        List<INDArray> result = this.rede.feedForward(dados);

        double rawOutput = result.get(1).getDouble(0);
        boolean podePular = rawOutput >= 0.5f;

        if (podePular) {
            this.passaro.pular();
        }
    }

    public void update(float deltaTime) {
        this.imaginar();
    }

    private float normalize(float input, float min, float max) {
        return (input - ((max + min) / 2)) / max;
    }
}
