package com.example.bankcards;

import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.entity.enums.AuthRole;
import com.example.bankcards.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

//    @Bean
    CommandLineRunner runner(PasswordEncoder passwordEncoder, UserRepository userRepository){
        return args -> {
            AuthUser auth = new AuthUser("Admin", passwordEncoder.encode("Admin"), AuthRole.ADMIN);
            auth.setEmail("Admin");
            auth.setFullName("Adminov Admin");
            userRepository.save(auth);
        };
    }
}
