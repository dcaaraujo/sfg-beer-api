package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<Page<BeerDTO>> getAllBeers(
            @RequestParam Optional<String> beerName,
            @RequestParam Optional<BeerStyle> beerStyle,
            @RequestParam Optional<Boolean> showInventory,
            @RequestParam Optional<Integer> page
    ) {
        int pageOrDefault = page.orElse(0);
        Page<BeerDTO> result;
        if (beerName.isPresent() && beerStyle.isPresent()) {
            result =
                    beerService.fetchBeersWithNameAndStyle(
                            beerName.get(), beerStyle.get(), pageOrDefault);
        } else if (beerName.isPresent()) {
            result = beerService.fetchBeersWithNameContaining(beerName.get(), 0);
        } else if (beerStyle.isPresent()) {
            result = beerService.fetchBeersWithStyle(beerStyle.get(), 0);
        } else {
            result = beerService.fetchAllBeers(0);
        }
        if (showInventory.isPresent() && !showInventory.get()) {
            result.forEach(beer -> beer.setQuantityOnHand(null));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable UUID id) {
        log.info("Fetching beer with ID {}", id);
        return ResponseEntity.of(beerService.fetchBeer(id));
    }

    @PostMapping
    public ResponseEntity<Void> createBeer(
            @RequestBody @Validated BeerDTO beer, UriComponentsBuilder builder) {
        var savedBeer = beerService.saveBeer(beer);
        var uri = builder.path("/api/v1/beer/{id}").buildAndExpand(savedBeer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateBeer(@PathVariable UUID id, @RequestBody BeerDTO beer) {
        var result = beerService.updateBeer(id, beer);
        if (result.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> patchBeer(@PathVariable UUID id, @RequestBody BeerDTO beer) {
        beerService.patchBeer(id, beer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable("id") UUID id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}
