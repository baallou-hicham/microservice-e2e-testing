package net.baallou.customerservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.baallou.customerservice.dto.CustomerDTO;
import net.baallou.customerservice.services.CustomerService;
import net.baallou.customerservice.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer API", description = "Gestion des clients")
@RequiredArgsConstructor
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Créer un client")
    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    @Operation(summary = "Récupérer tout les clients")
    @GetMapping
    public List<CustomerDTO> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Récupérer un client par ID")
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Rechercher des clients par mot clé")
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String keyword) {
        return customerService.searchCustomers(keyword);
    }

    @Operation(summary = "Modifier un client")
    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @Operation(summary = "Suppression un client")
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
