package net.baallou.customerservice.mapper;

import net.baallou.customerservice.dto.CustomerDTO;
import net.baallou.customerservice.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public CustomerDTO fromCustomer(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public List<CustomerDTO> fromListCustomers(List<Customer> customers) {
        return customers.stream().map(c->modelMapper.map(c, CustomerDTO.class)).collect(Collectors.toList());
    }

    public Customer toCustomer(CustomerDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }
}
