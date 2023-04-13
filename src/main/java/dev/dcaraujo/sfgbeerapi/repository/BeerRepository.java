package dev.dcaraujo.sfgbeerapi.repository;

import dev.dcaraujo.sfgbeerapi.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}
