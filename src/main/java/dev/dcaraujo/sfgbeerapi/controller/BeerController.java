package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<List<BeerDTO>> getAllBeers() {
        return ResponseEntity.ok(beerService.fetchAllBeers());
    }

    @GetMapping("{id}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable UUID id) {
        log.info("Fetching beer with ID {}", id);
        return ResponseEntity.of(beerService.fetchBeer(id));
    }

    @PostMapping
    public ResponseEntity<Void> createBeer(@RequestBody @Validated BeerDTO beer, UriComponentsBuilder builder) {
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
