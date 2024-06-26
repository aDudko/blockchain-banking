package net.dudko.project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Blockchain Banking",
                description = "Blockchain Banking REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Dudko Anatol",
                        email = "anatoly_dudko@icloud.com"
                ),
                license = @License(
                        name = "GPL-3.0 license",
                        url = "https://github.com/aDudko/blockchain-banking?tab=GPL-3.0-1-ov-file"
                )
        )
)
@SpringBootApplication
public class BlockchainBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainBankingApplication.class, args);
    }

}
