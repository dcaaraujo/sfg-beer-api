package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<List<Beer>> getBeers() {
        log.info("Fetching all beers");
        return ResponseEntity.ok(beerService.getBeers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beer> getBeerById(@PathVariable UUID id) {
        log.info("Fetching beer {}", id);
        var beer = beerService.getBeerById(id);
        return ResponseEntity.of(beer);
    }
}
