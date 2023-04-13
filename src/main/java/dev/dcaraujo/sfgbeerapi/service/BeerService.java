package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> fetchAllBeers();

    Optional<BeerDTO> fetchBeer(UUID id);

    BeerDTO saveBeer(BeerDTO beerDto);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDto);

    void patchBeer(UUID id, BeerDTO beerDto);

    void deleteBeer(UUID id);
}
