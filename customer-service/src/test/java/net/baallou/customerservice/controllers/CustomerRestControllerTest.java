package net.baallou.customerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.baallou.customerservice.dto.CustomerDTO;
import net.baallou.customerservice.exceptions.EmailAlreadyExistException;
import net.baallou.customerservice.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCustomer() throws Exception {
        CustomerDTO input = CustomerDTO.builder()
                .firstName("Ismail")
                .lastName("Matar")
                .email("ismail@gmail.com")
                .build();

        CustomerDTO output = CustomerDTO.builder()
                .id(1L)
                .firstName("Ismail")
                .lastName("Matar")
                .email("ismail@gmail.com")
                .build();

        Mockito.when(customerService.addCustomer(Mockito.any()))
                .thenReturn(output);

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("ismail@gmail.com"));
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        List<CustomerDTO> customers = List.of(
                CustomerDTO.builder().firstName("Mohamed").email("med@gmail.com").build(),
                CustomerDTO.builder().firstName("Ahmed").email("ahmed@gmail.com").build()
        );

        Mockito.when(customerService.getAllCustomers())
                .thenReturn(customers);

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldGetCustomerById() throws Exception {
        CustomerDTO customer = CustomerDTO.builder()
                .id(1L)
                .firstName("Ismail")
                .email("ismail@gmail.com")
                .build();

        Mockito.when(customerService.getCustomerById(1L))
                .thenReturn(customer);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());

        Mockito.verify(customerService).deleteCustomer(1L);
    }

    @Test
    void shouldReturnErrorWhenEmailExists() throws Exception {
        CustomerDTO input = CustomerDTO.builder()
                .email("test@gmail.com")
                .build();

        Mockito.when(customerService.addCustomer(Mockito.any()))
                .thenThrow(new EmailAlreadyExistException("Email already exists"));

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }
}