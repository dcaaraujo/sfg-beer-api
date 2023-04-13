package dev.dcaraujo.sfgbeerapi.util;

import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import dev.dcaraujo.sfgbeerapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DatabaseBootstrapTest {
    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private DatabaseBootstrap dbBootstrap;

    @BeforeEach
    public void setUp() {
        dbBootstrap = new DatabaseBootstrap(beerRepository, customerRepository);
    }

    @Test
    public void runBootstrap() throws Exception {
        dbBootstrap.run((String) null);
        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}
