package net.baallou.customerservice;

import net.baallou.customerservice.entities.Customer;
import net.baallou.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

//    @Bean
//    @Profile("!test")
//    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
//        return args -> {
//            customerRepository.save(Customer.builder().firstName("Hicham")
//                    .lastName("Baallou").email("hicham@gmail.com").build());
//            customerRepository.save(Customer.builder().firstName("Mohamed")
//                    .lastName("Hamdaoui").email("mohamed@gmail.com").build());
//            customerRepository.save(Customer.builder().firstName("Rida")
//                    .lastName("Tahiri").email("rida@gmail.com").build());
//        };
//    }
}
