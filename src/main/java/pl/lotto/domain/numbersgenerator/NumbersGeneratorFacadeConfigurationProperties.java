package pl.lotto.domain.numbersgenerator;

import lombok.*;
import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "lotto.number-generator.facade")
@Builder
public record NumbersGeneratorFacadeConfigurationProperties(int lowerBand, int upperBand, int count) {
}