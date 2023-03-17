package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerForm;
import dev.dcaraujo.sfgbeerapi.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<Beer> fetchAllBeers();

    Optional<Beer> fetchBeer(UUID id);

    Beer saveBeer(BeerForm form);

    void updateBeer(UUID id, BeerForm form);

    void patchBeer(UUID id, BeerForm form);

    void deleteBeer(UUID id);
}
