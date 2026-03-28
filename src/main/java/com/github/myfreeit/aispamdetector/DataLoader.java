package com.github.myfreeit.aispamdetector;

import jakarta.annotation.Nonnull;
import org.tribuo.Example;
import org.tribuo.classification.Label;
import org.tribuo.data.text.TextFeatureExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private static final String DATA_PATH = "src/main/resources/spam_data.csv";
    private static final String UNKNOWN_TOKEN = " unknown_word_token";

    @Nonnull
    public List<Example<Label>> loadExamples(TextFeatureExtractor<Label> extractor) throws IOException {
        List<Example<Label>> examples = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(DATA_PATH));

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                String labelStr = parts[0].trim();
                String textStr = parts[1].trim() + UNKNOWN_TOKEN;

                Example<Label> ex = extractor.extract(new Label(labelStr), textStr);
                if (ex.size() > 0) {
                    examples.add(ex);
                }
            }
        }
        return examples;
    }
}
