package dev.dcaraujo.sfgbeerapi.service;

import dev.dcaraujo.sfgbeerapi.dto.BeerDTO;
import dev.dcaraujo.sfgbeerapi.dto.CustomerDTO;
import dev.dcaraujo.sfgbeerapi.mapper.CustomerMapper;
import dev.dcaraujo.sfgbeerapi.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public List<CustomerDTO> fetchAllCustomers() {
        return repository.findAll().stream().map(mapper::customerToCustomerDto).toList();
    }

    @Override
    public Optional<CustomerDTO> fetchCustomer(UUID id) {
        return repository.findById(id).map(mapper::customerToCustomerDto);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        var customer = mapper.customerDtoToCustomer(dto);
        return mapper.customerToCustomerDto(repository.save(customer));
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO dto) {
        var atomicReference = new AtomicReference<Optional<CustomerDTO>>();
        repository.findById(id).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(dto.getName());
            var result = Optional.of(mapper.customerToCustomerDto(repository.save(foundCustomer)));
            atomicReference.set(result);
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }

    @Override
    public void patchCustomer(UUID id, CustomerDTO form) {
        var query = repository.findById(id);
        if (query.isEmpty()) {
            return;
        }
        var customer = query.get();
        if (StringUtils.hasText(form.getName())) {
            customer.setName(form.getName());
        }
        repository.save(customer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        repository.deleteById(id);
    }
}
