package pl.lotto.infrastucture.numbersgenerator.http;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;
import pl.lotto.domain.numbersgenerator.*;

import java.time.*;

@Configuration
public class WinningNumberGenerationClientConfig {

    @Autowired
    NumbersGeneratorRestTemplateConfigurationProperties numbersGeneratorRestTemplateConfigurationProperties;

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder().errorHandler(restTemplateResponseErrorHandler)
                                        .setConnectTimeout(Duration.ofMillis(
                                                numbersGeneratorRestTemplateConfigurationProperties.connectionTimeout()))
                                        .setReadTimeout(Duration.ofMillis(
                                                numbersGeneratorRestTemplateConfigurationProperties.readTimeout()))
                                        .build();
    }

    @Bean
    public WinningNumberGenerable remoteNumberGeneratorClient(
            RestTemplate restTemplate
                                                             ) {
        return new WinningNumberGeneratorRestTemplate(restTemplate,
                numbersGeneratorRestTemplateConfigurationProperties.uri(),
                numbersGeneratorRestTemplateConfigurationProperties.port());
    }
}