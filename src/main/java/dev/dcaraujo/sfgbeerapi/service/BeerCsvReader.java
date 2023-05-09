package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerCsvRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvReader {
    List<BeerCsvRecord> parse(File file);
}
