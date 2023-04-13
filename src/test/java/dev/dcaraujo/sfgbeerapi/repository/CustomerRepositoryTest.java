package dev.dcaraujo.sfgbeerapi.repository;

import dev.dcaraujo.sfgbeerapi.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;

    @Test
    public void persistingACustomer() {
        var customer = Customer.builder().name("John Smith").build();
        var result = repository.save(customer);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
    }
}
