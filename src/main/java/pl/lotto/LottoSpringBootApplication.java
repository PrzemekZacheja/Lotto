package pl.lotto;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.data.mongodb.repository.config.*;
import org.springframework.scheduling.annotation.*;
import pl.lotto.domain.numbersgenerator.*;
import pl.lotto.infrastucture.numbersgenerator.http.*;

@SpringBootApplication
@EnableConfigurationProperties({NumbersGeneratorFacadeConfigurationProperties.class, NumbersGeneratorRestTemplateConfigurationProperties.class})
@EnableScheduling
@EnableMongoRepositories
public class LottoSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoSpringBootApplication.class, args);
    }

}