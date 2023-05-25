package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.dto.CustomerDTO;
import dev.dcaraujo.sfgbeerapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.fetchAllCustomers());
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.of(customerService.fetchCustomer(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDTO customer, UriComponentsBuilder builder) {
        var savedCustomer = customerService.saveCustomer(customer);
        var uri =
                builder.path("/api/v1/customer/{id}").buildAndExpand(savedCustomer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customer) {
        customerService.updateCustomer(id, customer);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> patchCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customer) {
        customerService.patchCustomer(id, customer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
