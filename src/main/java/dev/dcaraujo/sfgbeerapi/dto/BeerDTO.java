package dev.dcaraujo.sfgbeerapi.dto;

import dev.dcaraujo.sfgbeerapi.model.BeerStyle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {
    private UUID id;

    private Integer version;

    @NotNull
    @NotBlank
    private String beerName;

    private BeerStyle beerStyle;

    private String upc;

    private Integer quantityOnHand;

    private BigDecimal price;

    private LocalDateTime createdDate;

    private LocalDateTime updateDate;
}
