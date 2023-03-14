package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.model.Customer;
import dev.dcaraujo.sfgbeerapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        log.info("Fetching all customers");
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable UUID id) {
        log.info("Fetching customer with ID {}", id);
        return ResponseEntity.of(customerService.getCustomer(id));
    }
}
