package com.github.myfreeit.aispamdetector;

import com.oracle.labs.mlrg.olcut.provenance.ConfiguredObjectProvenance;
import com.oracle.labs.mlrg.olcut.provenance.impl.ConfiguredObjectProvenanceImpl;
import org.tribuo.Feature;
import org.tribuo.classification.Label;
import org.tribuo.data.text.TextFeatureExtractor;
import org.tribuo.data.text.TextPipeline;
import org.tribuo.data.text.impl.TextFeatureExtractorImpl;
import org.tribuo.util.tokens.impl.BreakIteratorTokenizer;

import java.util.List;
import java.util.Locale;

public class TextProcessor {
    private static final String LANGUAGE_TAG = "ru-RU";
    private static final String PIPELINE_NAME = "TextPipeline";
    private static final int MIN_TOKEN_LENGTH = 2;

    public TextFeatureExtractor<Label> createExtractor() {
        var tokenizer = new BreakIteratorTokenizer(Locale.forLanguageTag(LANGUAGE_TAG));

        TextPipeline pipeline = new TextPipeline() {
            @Override
            public List<Feature> process(String tag, String data) {
                return tokenizer.tokenize(data).stream()
                        .map(token -> token.text.toLowerCase())
                        .filter(text -> text.length() >= MIN_TOKEN_LENGTH)
                        .distinct()
                        .map(text -> new Feature(text, 1.0))
                        .toList();
            }

            @Override
            public ConfiguredObjectProvenance getProvenance() {
                return new ConfiguredObjectProvenanceImpl(this, PIPELINE_NAME);
            }
        };

        return new TextFeatureExtractorImpl<>(pipeline);
    }
}
