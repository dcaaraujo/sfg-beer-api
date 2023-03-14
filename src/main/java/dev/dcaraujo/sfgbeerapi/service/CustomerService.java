package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getCustomers();

    Optional<Customer> getCustomer(UUID id);
}
