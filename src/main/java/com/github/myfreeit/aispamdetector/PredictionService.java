package com.github.myfreeit.aispamdetector;

import org.tribuo.Example;
import org.tribuo.Feature;
import org.tribuo.Model;
import org.tribuo.Prediction;
import org.tribuo.classification.Label;
import org.tribuo.data.text.TextFeatureExtractor;
import org.tribuo.impl.ArrayExample;

public class PredictionService {
    private static final String UNKNOWN_LABEL = "unknown";
    private static final String UNKNOWN_TOKEN = "unknown_word_token";
    private static final double FEATURE_VALUE = 1.0;

    public Prediction<Label> predict(Model<Label> model,
                                     TextFeatureExtractor<Label> extractor,
                                     String input) {
        Example<Label> rawEx = extractor.extract(new Label(UNKNOWN_LABEL), input);
        ArrayExample<Label> finalEx = new ArrayExample<>(new Label(UNKNOWN_LABEL));

        for (Feature f : rawEx) {
            if (model.getFeatureIDMap().getID(f.getName()) != -1) {
                finalEx.add(f.getName(), FEATURE_VALUE);
            }
        }
        finalEx.add(UNKNOWN_TOKEN, FEATURE_VALUE);

        return model.predict(finalEx);
    }

    public String formatResult(Prediction<Label> prediction) {
        return String.format("Result: %s (Confidence: %.2f)",
                prediction.getOutput().getLabel(),
                prediction.getOutput().getScore());
    }
}
