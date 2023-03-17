package dev.dcaraujo.sfgbeerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dcaraujo.sfgbeerapi.dto.BeerForm;
import dev.dcaraujo.sfgbeerapi.model.Beer;
import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import dev.dcaraujo.sfgbeerapi.service.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    public static final String ROOT_PATH = "/api/v1/beer";
    public static final String ROOT_PATH_ID = "/api/v1/beer/{id}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<UUID> idArgumentCaptor;

    @Captor
    private ArgumentCaptor<BeerForm> beerFormArgumentCaptor;

    @MockBean
    private BeerService beerService;

    @Test
    void fetchingAllBeers() throws Exception {
        var beers = List.of(createBeer(), createBeer(), createBeer());
        given(beerService.fetchAllBeers()).willReturn(beers);
        var request = get(ROOT_PATH).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(beers.size())));
    }

    @Test
    void fetchingBeerById() throws Exception {
        var beer = createBeer();
        given(beerService.fetchBeer(any())).willReturn(Optional.of(beer));
        var request = get(ROOT_PATH_ID, beer.getId()).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beer.getBeerName())));
    }

    @Test
    void beerNotFound() throws Exception {
        given(beerService.fetchBeer(any())).willReturn(Optional.empty());
        var request = get(ROOT_PATH_ID, UUID.randomUUID()).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    void creatingAbeer() throws Exception {
        var form = createForm();
        given(beerService.saveBeer(any())).willReturn(createBeer());
        var request = post(ROOT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form));
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updatingABeer() throws Exception {
        var beerId = UUID.randomUUID();
        var form = createForm();
        var request = put(ROOT_PATH_ID, beerId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form));
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(beerService).updateBeer(idArgumentCaptor.capture(), any());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(beerId);
    }

    @Test
    void deletingABeer() throws Exception {
        var beerId = UUID.randomUUID();
        var request = delete(ROOT_PATH_ID, beerId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(beerService).deleteBeer(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(beerId);
    }

    @Test
    void patchingABeer() throws Exception {
        var beerId = UUID.randomUUID();
        var beerName = "Windhoek";
        var map = new HashMap<String, Object>();
        map.put("beerName", beerName);
        var request = patch(ROOT_PATH_ID, beerId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map));
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(beerService).patchBeer(idArgumentCaptor.capture(), beerFormArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(beerId);
        assertThat(beerFormArgumentCaptor.getValue().getBeerName()).isEqualTo(beerName);
    }

    private static BeerForm createForm() {
        var form = new BeerForm();
        form.setBeerName("Castle");
        form.setBeerStyle(BeerStyle.LAGER);
        form.setUpc("1212323");
        form.setQuantityOnHand(100);
        form.setPrice(new BigDecimal("2.00"));
        return form;
    }

    private static Beer createBeer() {
        return Beer.builder()
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
    }
}
