package dev.dcaraujo.sfgbeerapi.mapper;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.model.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
