package pl.lotto.domain.numbersgenerator;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.facade")
@Builder
public record NumbersGeneratorFacadeConfigurationProperties(int lowerBand, int upperBand, int count) {
}