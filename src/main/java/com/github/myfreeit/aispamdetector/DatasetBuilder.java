package com.github.myfreeit.aispamdetector;

import jakarta.annotation.Nonnull;
import org.tribuo.Example;
import org.tribuo.MutableDataset;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.provenance.SimpleDataSourceProvenance;

import java.util.List;

public class DatasetBuilder {
    private static final String DATASET_NAME = "SpamCSV";

    @Nonnull
    public MutableDataset<Label> buildDataset(LabelFactory labelFactory,
                                              @Nonnull List<Example<Label>> examples) {
        var dsProv = new SimpleDataSourceProvenance(DATASET_NAME, labelFactory);
        var dataSource = new org.tribuo.datasource.ListDataSource<>(examples, labelFactory, dsProv);
        return new MutableDataset<>(dataSource);
    }
}
