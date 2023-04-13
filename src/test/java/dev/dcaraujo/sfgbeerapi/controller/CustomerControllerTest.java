package dev.dcaraujo.sfgbeerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dcaraujo.sfgbeerapi.dto.CustomerDTO;
import dev.dcaraujo.sfgbeerapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    public static final String ROOT_PATH = "/api/v1/customer";
    public static final String ROOT_PATH_ID = "/api/v1/customer/{id}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<UUID> idArgumentCaptor;

    @Captor
    private ArgumentCaptor<CustomerDTO> customerFormArgumentCaptor;

    @MockBean
    private CustomerService customerService;

    @Test
    public void fetchingAllCustomers() throws Exception {
        var customers = List.of(createCustomer(), createCustomer(), createCustomer());
        given(customerService.fetchAllCustomers()).willReturn(customers);
        var request = get(ROOT_PATH).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customers.size())));
    }

    @Test
    public void fetchingCustomerById() throws Exception {
        var customer = createCustomer();
        given(customerService.fetchCustomer(any())).willReturn(Optional.of(customer));
        var request = get(ROOT_PATH_ID, customer.getId()).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer.getId().toString())))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }

    @Test
    public void customerNotFound() throws Exception {
        given(customerService.fetchCustomer(any())).willReturn(Optional.empty());
        var request = get(ROOT_PATH_ID, UUID.randomUUID()).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    public void creatingACustomer() throws Exception {
        var form = CustomerDTO.builder().name("John Smith").build();

        given(customerService.saveCustomer(any())).willReturn(createCustomer());
        var request = post(ROOT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form));
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void updatingACustomer() throws Exception {
        var customerId = UUID.randomUUID();
        var form = CustomerDTO.builder().name("John Smith").build();
        var request = put(ROOT_PATH_ID, customerId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form));
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(customerService).updateCustomer(idArgumentCaptor.capture(), any());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(customerId);
    }

    @Test
    public void deletingACustomer() throws Exception {
        var customerId = UUID.randomUUID();
        var request = delete(ROOT_PATH_ID, customerId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(customerService).deleteCustomer(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(customerId);
    }

    @Test
    public void patchingACustomer() throws Exception {
        var customerId = UUID.randomUUID();
        var customerName = "Keanu Reeves";
        var map = new HashMap<String, Object>();
        map.put("name", customerName);
        var request = patch(ROOT_PATH_ID, customerId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map));
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(customerService).patchCustomer(idArgumentCaptor.capture(), customerFormArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(customerId);
        assertThat(customerFormArgumentCaptor.getValue().getName()).isEqualTo(customerName);
    }

    private static CustomerDTO createCustomer() {
        return CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("John Smith")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
