package dev.dcaraujo.sfgbeerapi.util;

import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.model.Customer;
import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import dev.dcaraujo.sfgbeerapi.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@AllArgsConstructor
public class DatabaseBootstrap implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        seedBeers();
        seedCustomers();
    }

    private void seedBeers() {
        if (beerRepository.count() != 0) {
            return;
        }
        var beer1 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        var beer2 = Beer.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        var beer3 = Beer.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
    }

    private void seedCustomers() {
        if (customerRepository.count() != 0) {
            return;
        }
        var customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        var customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        var customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
    }
}
