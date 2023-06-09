package pl.lotto.domain.numbersgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;

@Configuration
public class NumberGeneratorConfiguration {

    @Bean
    NumbersGeneratorRepository numbersGeneratorRepository() {
        return new NumbersGeneratorRepository() {
            @Override
            public WinnerNumbers save(WinnerNumbers winnerNumbers) {
                return null;
            }

            @Override
            public WinnerNumbers findWinnerNumbersByDrawDate(LocalDateTime localDateTime) {
                return null;
            }
        };
    }

    @Bean
    public NumbersGeneratorFacade numbersGeneratorFacade(NumberReceiverFacade numberReceiverFacade,
                                                         WinningNumberGenerable winningNumberGenerable,
                                                         NumbersGeneratorRepository repository,
                                                         NumbersGeneratorFacadeConfigurationProperties properties) {
        return new NumbersGeneratorFacade(numberReceiverFacade, winningNumberGenerable, repository, properties);
    }
}