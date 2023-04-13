package dev.dcaraujo.sfgbeerapi.mapper;

import dev.dcaraujo.sfgbeerapi.dto.CustomerDTO;
import dev.dcaraujo.sfgbeerapi.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
