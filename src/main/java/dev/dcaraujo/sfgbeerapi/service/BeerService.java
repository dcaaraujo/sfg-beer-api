package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<Beer> getBeers();

    Optional<Beer> getBeerById(UUID id);
}
