package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VendasApp.class, args);

    }
}
