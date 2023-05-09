package dev.dcaraujo.sfgbeerapi.util;

import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import dev.dcaraujo.sfgbeerapi.repository.CustomerRepository;
import dev.dcaraujo.sfgbeerapi.service.BeerCsvReaderImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BeerCsvReaderImpl.class, DatabaseBootstrap.class})
public class DatabaseBootstrapTest {
    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DatabaseBootstrap dbBootstrap;

    @Test
    public void runBootstrap() throws Exception {
        dbBootstrap.run((String) null);
        assertThat(beerRepository.count()).isEqualTo(2410);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}
