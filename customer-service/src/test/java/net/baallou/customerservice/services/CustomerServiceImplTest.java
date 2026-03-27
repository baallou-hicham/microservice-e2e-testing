package net.baallou.customerservice.services;

import net.baallou.customerservice.dto.CustomerDTO;
import net.baallou.customerservice.entities.Customer;
import net.baallou.customerservice.exceptions.CustomerAlreadyExistsException;
import net.baallou.customerservice.exceptions.CustomerNotFoundException;
import net.baallou.customerservice.mapper.CustomerMapper;
import net.baallou.customerservice.repositories.CustomerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .firstName("Hicham")
                .lastName("Baallou")
                .email("hicham@gmail.com")
                .build();

        customerDTO = CustomerDTO.builder()
                .id(1L)
                .firstName("Hicham")
                .lastName("Baallou")
                .email("hicham@gmail.com")
                .build();
    }

    @Test
    void shouldCreateCustomerWhenEmailNotExists() {
        // Arrange
        when(customerRepository.findByEmail(customerDTO.getEmail()))
                .thenReturn(Optional.empty());

        when(customerMapper.fromCustomerDTO(customerDTO))
                .thenReturn(customer);

        when(customerRepository.save(customer))
                .thenReturn(customer);

        when(customerMapper.fromCustomer(customer))
                .thenReturn(customerDTO);

        // Act
        CustomerDTO result = customerService.addCustomer(customerDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(customerDTO.getEmail());

        verify(customerRepository).findByEmail(customerDTO.getEmail());
        verify(customerRepository).save(customer);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(customerRepository.findByEmail(customerDTO.getEmail()))
                .thenReturn(Optional.of(customer));

        // Act & Assert
        assertThatThrownBy(() -> customerService.addCustomer(customerDTO))
                .isInstanceOf(CustomerAlreadyExistsException.class)
                .hasMessageContaining("already exists");

        verify(customerRepository).findByEmail(customerDTO.getEmail());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldGetCustomerById() {
        // Arrange
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerMapper.fromCustomer(customer))
                .thenReturn(customerDTO);

        // Act
        CustomerDTO result = customerService.getCustomerById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(customerRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.getCustomerById(1L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("not found");

        verify(customerRepository).findById(1L);
    }
}