package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerCsvRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeerCsvReaderImplTest {

    @Autowired
    private BeerCsvReader csvReader;

    @Test
    public void readFromCsv() throws Exception {
        var file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCsvRecord> records = csvReader.parse(file);
        assertThat(records.size()).isGreaterThan(0);
    }
}
