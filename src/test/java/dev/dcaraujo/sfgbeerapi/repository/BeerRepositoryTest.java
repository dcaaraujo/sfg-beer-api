package dev.dcaraujo.sfgbeerapi.repository;

import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Test
    void persistingABeer() {
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
    void cannotPersistABeerWithLongName() {
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
}
