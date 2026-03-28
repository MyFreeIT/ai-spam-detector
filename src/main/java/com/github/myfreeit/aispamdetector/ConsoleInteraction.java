package com.github.myfreeit.aispamdetector;

import org.tribuo.Model;
import org.tribuo.Prediction;
import org.tribuo.classification.Label;
import org.tribuo.data.text.TextFeatureExtractor;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleInteraction {
    private static final String EXIT_COMMAND = "exit";
    private static final String PROMPT = "Input: ";

    public void startInteraction(Model<Label> model, TextFeatureExtractor<Label> extractor) {
        PredictionService predictionService = new PredictionService();
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.print(PROMPT);

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                    break;
                }

                Prediction<Label> prediction = predictionService.predict(model, extractor, input);
                String result = predictionService.formatResult(prediction);
                System.out.println(result);

                System.out.print(PROMPT);
            }
        }
    }
}