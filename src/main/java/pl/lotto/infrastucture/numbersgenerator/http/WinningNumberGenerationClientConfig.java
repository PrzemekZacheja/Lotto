package pl.lotto.infrastucture.numbersgenerator.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;

import java.time.Duration;

@Configuration
public class WinningNumberGenerationClientConfig {

    @Bean
    public WinningNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate,
                                                              @Value("${lotto.numbergenerator.http.client.config.uri})") String uri,
                                                              @Value("${lotto.numbergenerator.http.client.config.port}") int port) {
        return new WinningNumberGeneratorRestTemplate(restTemplate, uri, port);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler,
                                     @Value("${lotto.numbergenerator.http.client.config.connection-timeout:1000}") int connectionTimeout,
                                     @Value("${lotto.numbergenerator.http.client.config.read-timeout:1000}") int readTimeout) {
        return new RestTemplateBuilder().errorHandler(restTemplateResponseErrorHandler)
                                        .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                                        .setReadTimeout(Duration.ofMillis(readTimeout))
                                        .build();
    }

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }
}