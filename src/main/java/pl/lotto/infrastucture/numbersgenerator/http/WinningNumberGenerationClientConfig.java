package pl.lotto.infrastucture.numbersgenerator.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;

import java.time.Duration;

@Configuration
public class WinningNumberGenerationClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

    @Bean
    public WinningNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate) {
        return new WinningNumberGeneratorRestTemplate(restTemplate);
    }
}