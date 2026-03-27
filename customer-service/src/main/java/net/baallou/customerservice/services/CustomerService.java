package net.baallou.customerservice.services;

import net.baallou.customerservice.dto.CustomerDTO;
import net.baallou.customerservice.exceptions.EmailAlreadyExistException;

import java.util.List;

public interface CustomerService {
    public CustomerDTO addCustomer(CustomerDTO customerDTO);
    public CustomerDTO getCustomerById(Long id);
    public List<CustomerDTO> getAllCustomers();
    public List<CustomerDTO> searchCustomers(String keyword);
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    public void deleteCustomer(Long id);
}
