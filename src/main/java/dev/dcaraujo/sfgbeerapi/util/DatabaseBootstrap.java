package dev.dcaraujo.sfgbeerapi.util;

import dev.dcaraujo.sfgbeerapi.dto.BeerCsvRecord;
import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.model.Customer;
import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import dev.dcaraujo.sfgbeerapi.repository.CustomerRepository;
import dev.dcaraujo.sfgbeerapi.service.BeerCsvReader;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@AllArgsConstructor
public class DatabaseBootstrap implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvReader beerCsvReader;

    @Override
    public void run(String... args) throws Exception {
        seedBeers();
        seedCustomers();
    }

    private void seedBeers() {
        if (beerRepository.count() != 0) {
            return;
        }
        try {
            var file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            var beers =
                    beerCsvReader.parse(file).stream().map(this::mapBeerCsvRecordToModel).toList();
            beerRepository.saveAll(beers);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Beer mapBeerCsvRecordToModel(BeerCsvRecord record) {
        var beerStyle =
                switch (record.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)",
                            "American Black Ale",
                            "Belgian Dark Ale",
                            "American Blonde Ale" -> BeerStyle.ALE;
                    case "American IPA",
                            "American Double / Imperial IPA",
                            "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer",
                            "Winter Warmer",
                            "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };
        return Beer.builder()
                .beerName(StringUtils.abbreviate(record.getBeer(), 50))
                .beerStyle(beerStyle)
                .price(BigDecimal.TEN)
                .upc(record.getRow().toString())
                .quantityOnHand(record.getCount())
                .build();
    }

    private void seedCustomers() {
        if (customerRepository.count() != 0) {
            return;
        }
        var customer1 =
                Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Customer 1")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

        var customer2 =
                Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Customer 2")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

        var customer3 =
                Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Customer 3")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
    }
}
