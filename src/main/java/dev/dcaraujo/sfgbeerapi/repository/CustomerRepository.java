package dev.dcaraujo.sfgbeerapi.repository;

import dev.dcaraujo.sfgbeerapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
