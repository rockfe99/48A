package net.datasa.web4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Web4Application {

    public static void main(String[] args) {
        SpringApplication.run(Web4Application.class, args);
    }

}
