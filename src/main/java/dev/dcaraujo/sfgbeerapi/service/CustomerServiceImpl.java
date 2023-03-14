package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        var customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Smith")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        var customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Doe")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
    }

    @Override
    public List<Customer> getCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<Customer> getCustomer(UUID id) {
        return Optional.ofNullable(customerMap.get(id));
    }
}
