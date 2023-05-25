package dev.dcaraujo.sfgbeerapi.repository;

import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.service.BeerCsvReaderImpl;
import dev.dcaraujo.sfgbeerapi.util.DatabaseBootstrap;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({DatabaseBootstrap.class, BeerCsvReaderImpl.class})
class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Test
    public void persistingABeer() {
        var beer = Beer.builder()
                .beerName("Castle")
                .beerStyle(BeerStyle.LAGER)
                .upc("1234")
                .price(new BigDecimal("13.13"))
                .quantityOnHand(20)
                .build();
        var result = repository.save(beer);
        repository.flush();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void cannotPersistABeerWithLongName() {
        var beerName = RandomStringUtils.randomAlphanumeric(51);
        var beer = Beer.builder()
                .beerName(beerName)
                .beerStyle(BeerStyle.LAGER)
                .upc("1234")
                .price(new BigDecimal("13.13"))
                .quantityOnHand(20)
                .build();
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(beer);
            repository.flush();
        });
    }

    @Test
    public void fetchingBeersByName() {
        var list = repository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
        assertThat(list).hasSize(336);
    }
}
