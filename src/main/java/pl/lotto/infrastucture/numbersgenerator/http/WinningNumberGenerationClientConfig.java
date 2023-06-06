package pl.lotto.infrastucture.numbersgenerator.http;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class WinningNumberGenerationClientConfig {

    private final NumbersGeneratorRestTemplateConfigurationProperties configuration;

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(
            RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(configuration.timeout()))
                .setReadTimeout(Duration.ofMillis(configuration.readTimeout()))
                .build();
    }

    @Bean
    public WinningNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate) {

        return new WinningNumberGeneratorRestTemplate(restTemplate, configuration.uri(), configuration.port());
    }

}