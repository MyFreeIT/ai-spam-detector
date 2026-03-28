package com.github.myfreeit.aispamdetector;

import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.classification.Label;
import org.tribuo.classification.sgd.linear.LinearSGDTrainer;
import org.tribuo.classification.sgd.objectives.LogMulticlass;
import org.tribuo.math.optimisers.AdaGrad;

public class ModelTrainer {
    private static final int EPOCHS = 10000;
    private static final int MINIBATCH_SIZE = 10;
    private static final long SEED = 1L;
    private static final double LEARNING_RATE = 0.1;

    public Model<Label> trainModel(MutableDataset<Label> dataset) {
        var trainer = new LinearSGDTrainer(
                new LogMulticlass(),
                new AdaGrad(LEARNING_RATE),
                EPOCHS,
                MINIBATCH_SIZE,
                SEED
        );

        Model<Label> model = trainer.train(dataset);
        System.out.printf("--- Trained on %d examples (%d generations) ---%n", dataset.size(), EPOCHS);

        return model;
    }
}
