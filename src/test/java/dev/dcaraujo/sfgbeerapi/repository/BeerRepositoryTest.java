package dev.dcaraujo.sfgbeerapi.repository;

import dev.dcaraujo.sfgbeerapi.model.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Test
    void persistingABeer() {
        var beer = Beer.builder().beerName("Castle").build();
        var result = repository.save(beer);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
    }
}
