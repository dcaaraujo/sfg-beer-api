package dev.dcaraujo.sfgbeerapi.controller;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.mapper.BeerMapper;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeerControllerIntegrationTest {
    @Autowired
    private BeerController controller;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    private final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

    @Test
    public void controllerReturnsAListOfBeers() {
        var response = controller.getAllBeers();
        var beers = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beers).isNotNull().isNotEmpty();
    }

    @Transactional
    @Rollback
    @Test
    public void controllerReturnsAnEmptyListOfBeers() {
        beerRepository.deleteAll();
        var response = controller.getAllBeers();
        var beers = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beers).isNotNull().isEmpty();
    }

    @Test
    public void controllerReturnsABeer() {
        var beer = beerRepository.findAll().get(0);
        var result = controller.getBeer(beer.getId());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void controllerCannotFindABeer() {
        var result = controller.getBeer(UUID.randomUUID());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNull();
    }

    @Test
    public void createNewBeer() {
        var form = BeerDTO.builder()
                .beerName("Castle")
                .beerStyle(BeerStyle.LAGER)
                .upc("1234")
                .price(new BigDecimal("13.13"))
                .quantityOnHand(20)
                .build();

        var result = controller.createBeer(form, uriComponentsBuilder);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getHeaders().getLocation()).isNotNull();

        List<String> locationPath = List.of(result.getHeaders().getLocation().getPath().split("/"));
        String uuid = CollectionUtils.lastElement(locationPath);
        assertThat(uuid).isNotNull();

        var savedBeer = beerRepository.findById(UUID.fromString(uuid));
        assertThat(savedBeer).isNotNull();
    }

    @Test
    public void updateABeer() {
        final String newBeerName = "UPDATED";
        var beer = beerRepository.findAll().get(0);
        var dto = beerMapper.beerToBeerDto(beer);
        dto.setId(null);
        dto.setVersion(null);
        dto.setBeerName(newBeerName);
        var response = controller.updateBeer(beer.getId(), dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(newBeerName);
    }
}
