package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Page<BeerDTO> fetchAllBeers(int page);

    Optional<BeerDTO> fetchBeer(UUID id);

    Page<BeerDTO> fetchBeersWithNameContaining(@NonNull String beerName, int page);

    Page<BeerDTO> fetchBeersWithStyle(@NonNull BeerStyle style, int page);

    Page<BeerDTO> fetchBeersWithNameAndStyle(
            @NonNull String beerName, @NonNull BeerStyle style, int page);

    BeerDTO saveBeer(BeerDTO beerDto);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDto);

    void patchBeer(UUID id, BeerDTO beerDto);

    void deleteBeer(UUID id);
}
