package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> fetchAllCustomers();

    Optional<CustomerDTO> fetchCustomer(UUID uuid);

    CustomerDTO saveCustomer(CustomerDTO form);

    Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO form);

    void patchCustomer(UUID id, CustomerDTO form);

    void deleteCustomer(UUID id);
}
