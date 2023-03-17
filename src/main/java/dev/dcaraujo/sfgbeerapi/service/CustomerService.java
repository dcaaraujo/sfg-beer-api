package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.CustomerForm;
import dev.dcaraujo.sfgbeerapi.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> fetchAllCustomers();

    Optional<Customer> fetchCustomer(UUID uuid);

    Customer saveCustomer(CustomerForm form);

    void updateCustomer(UUID id, CustomerForm form);

    void patchCustomer(UUID id, CustomerForm form);

    void deleteCustomer(UUID id);
}
