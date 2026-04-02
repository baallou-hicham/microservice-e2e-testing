package net.baallou.accountservice.clients;

import net.baallou.accountservice.dto.CustomerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    @GetMapping("/api/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}