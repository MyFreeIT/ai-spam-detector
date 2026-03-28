package com.github.myfreeit.aispamdetector;

import org.tribuo.Example;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.data.text.TextFeatureExtractor;

import java.io.IOException;
import java.util.List;

public class SpamDetectorApp {
    public static void main(String[] args) {

        // Initialize components
        TextProcessor textProcessor = new TextProcessor();
        DataLoader dataLoader = new DataLoader();
        DatasetBuilder datasetBuilder = new DatasetBuilder();
        ModelTrainer modelTrainer = new ModelTrainer();
        ConsoleInteraction consoleInteraction = new ConsoleInteraction();

        // Creating components
        LabelFactory labelFactory = new LabelFactory();
        TextFeatureExtractor<Label> extractor = textProcessor.createExtractor();

        // Load data
        List<Example<Label>> examples = null;
        try {
            examples = dataLoader.loadExamples(extractor);
        } catch (IOException e) {
            System.err.printf("Failed to load data: %s%n", e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Create a dataset
        MutableDataset<Label> dataset = datasetBuilder.buildDataset(labelFactory, examples);

        // Model training
        Model<Label> model = modelTrainer.trainModel(dataset);

        // Launch the console interface
        consoleInteraction.startInteraction(model, extractor);
    }
}

