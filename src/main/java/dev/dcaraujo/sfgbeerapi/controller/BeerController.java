package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.dto.BeerForm;
import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.service.BeerService;
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
@RequestMapping("api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<List<Beer>> getAllBeers() {
        var beers = beerService.fetchAllBeers();
        return ResponseEntity.ok(beers);
    }

    @GetMapping("{id}")
    public ResponseEntity<Beer> getBeer(@PathVariable UUID id) {
        return ResponseEntity.of(beerService.fetchBeer(id));
    }

    @PostMapping
    public ResponseEntity<Void> createBeer(@RequestBody BeerForm beer, UriComponentsBuilder builder) {
        Beer savedBeer = beerService.saveBeer(beer);
        var uri = builder.path("/api/v1/beer/{id}").buildAndExpand(savedBeer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateBeer(@PathVariable UUID id, @RequestBody BeerForm beer) {
        beerService.updateBeer(id, beer);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> patchBeer(@PathVariable UUID id, @RequestBody BeerForm beer) {
        beerService.patchBeer(id, beer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable("id") UUID id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}
