package pl.lotto.infrastucture.numbersgenerator.http;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;
import pl.lotto.domain.numbersgenerator.*;

import java.time.*;

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
    public WinningNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate,
                                                              @Value("${lotto.number_generator.http.client.config.uri" +
                                                                      ":http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com}") String uri,
                                                              @Value("${lotto.number_generator.http.client.config.port:9090}") int port) {

        return new WinningNumberGeneratorRestTemplate(restTemplate, uri, port);
    }
}