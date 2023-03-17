package dev.dcaraujo.sfgbeerapi.dto;

import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeerForm {
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
}
