package net.baallou.customerservice.services;

import net.baallou.customerservice.dto.CustomerDTO;
import net.baallou.customerservice.entities.Customer;
import net.baallou.customerservice.exceptions.CustomerAlreadyExistsException;
import net.baallou.customerservice.exceptions.CustomerNotFoundException;
import net.baallou.customerservice.exceptions.EmailAlreadyExistException;
import net.baallou.customerservice.mapper.CustomerMapper;
import net.baallou.customerservice.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        // Règle métier : email unique
        customerRepository.findByEmail(customerDTO.getEmail())
                .ifPresent(c -> {
                    throw new CustomerAlreadyExistsException(
                            "Customer with email " + customerDTO.getEmail() + " already exists"
                    );
                });
        Customer customer = customerMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException("Customer not found with id " + id);

        return customerMapper.fromCustomer(customer.get());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return customerMapper.fromListCustomers(allCustomers);
//        return customerRepository.findAll()
//                .stream()
//                .map(customerMapper::fromCustomer)
//                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        return customerMapper.fromListCustomers(customers);
//        return customerRepository.findByFirstNameContainsIgnoreCase(keyword)
//                .stream()
//                .map(customerMapper::fromCustomer)
//                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));

        // Vérifier l’unicité de l’email si modifié
        if (!existingCustomer.getEmail().equals(customerDTO.getEmail())) {
            customerRepository.findByEmail(customerDTO.getEmail())
                    .ifPresent(c -> {
                        throw new CustomerAlreadyExistsException(
                                "Customer with email " + customerDTO.getEmail() + " already exists"
                        );
                    });
        }

        existingCustomer.setFirstName(customerDTO.getFirstName());
        existingCustomer.setLastName(customerDTO.getLastName());
        existingCustomer.setEmail(customerDTO.getEmail());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.fromCustomer(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
        customerRepository.delete(customer);
    }
}
