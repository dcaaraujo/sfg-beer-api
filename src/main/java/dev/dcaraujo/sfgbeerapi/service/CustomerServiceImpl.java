package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.CustomerForm;
import dev.dcaraujo.sfgbeerapi.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
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

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> fetchAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<Customer> fetchCustomer(UUID uuid) {
        var customer = customerMap.get(uuid);
        return Optional.ofNullable(customer);
    }

    @Override
    public Customer saveCustomer(CustomerForm form) {
        var savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .name(form.getName())
                .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateCustomer(UUID id, CustomerForm form) {
        var existing = customerMap.get(id);
        existing.setName(form.getName());
    }

    @Override
    public void patchCustomer(UUID id, CustomerForm form) {
        var existing = customerMap.get(id);
        if (StringUtils.hasText(form.getName())) {
            existing.setName(form.getName());
        }
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerMap.remove(id);
    }
}
