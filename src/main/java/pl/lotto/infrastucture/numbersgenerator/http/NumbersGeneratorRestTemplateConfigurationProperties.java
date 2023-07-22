package pl.lotto.infrastucture.numbersgenerator.http;

import lombok.*;
import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "lotto.numbergenerator.http.client.config")
@Builder
public record NumbersGeneratorRestTemplateConfigurationProperties(
        String uri,
        int port,
        long connectionTimeout,
        long readTimeout) {
}