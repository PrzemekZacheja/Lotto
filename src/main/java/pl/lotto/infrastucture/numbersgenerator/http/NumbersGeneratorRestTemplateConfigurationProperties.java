package pl.lotto.infrastucture.numbersgenerator.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.http.client.config")
@Builder
public record NumbersGeneratorRestTemplateConfigurationProperties(
        String uri,
        int port,
        long connectionTimeout,
        long readTimeout) {
}