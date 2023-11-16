package pl.lotto.domain.numbersgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;

@Configuration
public class NumberGeneratorConfiguration {


    @Bean
    public NumbersGeneratorFacade numbersGeneratorFacade(
            WinningNumberGenerable winningNumberGenerable,
            NumbersGeneratorRepository repository,
            NumbersGeneratorFacadeConfigurationProperties properties,
            DrawDateFacade drawDateFacade) {
        return new NumbersGeneratorFacade(winningNumberGenerable, repository,
                                          properties, drawDateFacade);
    }
}