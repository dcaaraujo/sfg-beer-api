package dev.dcaraujo.sfgbeerapi.service;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.dcaraujo.sfgbeerapi.dto.BeerCsvRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BeerCsvReaderImpl implements BeerCsvReader {
    @Override
    public List<BeerCsvRecord> parse(File file) {
        try {
            return new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(file))
                    .withType(BeerCsvRecord.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            log.error("Received error when parsing CSV file. Reason {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
