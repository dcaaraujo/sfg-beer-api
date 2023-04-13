package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.mapper.BeerMapper;
import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@AllArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Override
    public List<BeerDTO> fetchAllBeers() {
        return repository.findAll().stream().map(mapper::beerToBeerDto).toList();
    }

    @Override
    public Optional<BeerDTO> fetchBeer(UUID id) {
        return repository.findById(id).map(mapper::beerToBeerDto);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDto) {
        var saved = repository.save(mapper.beerDtoToBeer(beerDto));
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
