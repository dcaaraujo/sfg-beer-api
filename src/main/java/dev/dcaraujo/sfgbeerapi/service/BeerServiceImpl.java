package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerForm;
import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BeerServiceImpl implements BeerService {
    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        var beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        var beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        var beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap = new HashMap<>();
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> fetchAllBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<Beer> fetchBeer(UUID id) {
        return Optional.ofNullable(beerMap.get(id));
    }

    @Override
    public Beer saveBeer(BeerForm form) {
        var savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(form.getBeerName())
                .beerStyle(form.getBeerStyle())
                .quantityOnHand(form.getQuantityOnHand())
                .upc(form.getUpc())
                .price(form.getPrice())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public void updateBeer(UUID id, BeerForm form) {
        var existing = beerMap.get(id);
        existing.setBeerName(form.getBeerName());
        existing.setPrice(form.getPrice());
        existing.setUpc(form.getUpc());
        existing.setQuantityOnHand(form.getQuantityOnHand());
    }

    @Override
    public void patchBeer(UUID id, BeerForm form) {
        var existing = beerMap.get(id);
        if (StringUtils.hasText(form.getBeerName())) {
            existing.setBeerName(form.getBeerName());
        }

        if (form.getBeerStyle() != null) {
            existing.setBeerStyle(form.getBeerStyle());
        }

        if (form.getPrice() != null) {
            existing.setPrice(form.getPrice());
        }

        if (form.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(form.getQuantityOnHand());
        }

        if (StringUtils.hasText(form.getUpc())) {
            existing.setUpc(form.getUpc());
        }
    }

    @Override
    public void deleteBeer(UUID id) {
        beerMap.remove(id);
    }
}
