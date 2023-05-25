package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.mapper.BeerMapper;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@AllArgsConstructor
public class BeerServiceImpl implements BeerService {
    private static final int DEFAULT_PAGE_SIZE = 25;
    private final BeerRepository repository;
    private final BeerMapper mapper;

    private static PageRequest getPageRequest(int page) {
        return PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("beerName").ascending());
    }

    @Override
    public Page<BeerDTO> fetchAllBeers(int page) {
        var pageRequest = getPageRequest(page);
        return repository.findAll(pageRequest).map(mapper::beerToBeerDto);
    }

    @Override
    public Optional<BeerDTO> fetchBeer(UUID id) {
        return repository.findById(id).map(mapper::beerToBeerDto);
    }

    @Override
    public Page<BeerDTO> fetchBeersWithNameContaining(@NonNull String beerName, int page) {
        var format = "%%%s%%".formatted(beerName);
        var pageRequest = getPageRequest(page);
        return repository
                .findAllByBeerNameIsLikeIgnoreCase(format, pageRequest)
                .map(mapper::beerToBeerDto);
    }

    @Override
    public Page<BeerDTO> fetchBeersWithStyle(@NonNull BeerStyle style, int page) {
        var pageRequest = getPageRequest(page);
        return repository.findAllByBeerStyle(style, pageRequest).map(mapper::beerToBeerDto);
    }

    @Override
    public Page<BeerDTO> fetchBeersWithNameAndStyle(
            @NonNull String beerName, @NonNull BeerStyle style, int page) {
        return repository
                .findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(
                        beerName, style, getPageRequest(page))
                .map(mapper::beerToBeerDto);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDto) {
        var beer = mapper.beerDtoToBeer(beerDto);
        var saved = repository.save(beer);
        return mapper.beerToBeerDto(saved);
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDto) {
        var atomicReference = new AtomicReference<Optional<BeerDTO>>();
        repository.findById(id).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beerDto.getBeerName());
            foundBeer.setBeerStyle(beerDto.getBeerStyle());
            foundBeer.setUpc(beerDto.getUpc());
            foundBeer.setPrice(beerDto.getPrice());
            var result = Optional.of(mapper.beerToBeerDto(repository.save(foundBeer)));
            atomicReference.set(result);
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public void patchBeer(UUID id, BeerDTO beerDto) {
        var query = repository.findById(id);
        if (query.isEmpty()) {
            return;
        }
        var existing = query.get();
        if (StringUtils.hasText(beerDto.getBeerName())) {
            existing.setBeerName(beerDto.getBeerName());
        }

        if (beerDto.getBeerStyle() != null) {
            existing.setBeerStyle(beerDto.getBeerStyle());
        }

        if (beerDto.getPrice() != null) {
            existing.setPrice(beerDto.getPrice());
        }

        if (beerDto.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beerDto.getQuantityOnHand());
        }

        if (StringUtils.hasText(beerDto.getUpc())) {
            existing.setUpc(beerDto.getUpc());
        }
        repository.save(existing);
    }

    @Override
    public void deleteBeer(UUID id) {
        repository.deleteById(id);
    }
}
