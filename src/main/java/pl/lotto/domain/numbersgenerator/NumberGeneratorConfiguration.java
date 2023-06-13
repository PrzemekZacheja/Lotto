package pl.lotto.domain.numbersgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

@Configuration
public class NumberGeneratorConfiguration {


    @Bean
    public NumbersGeneratorFacade numbersGeneratorFacade(NumberReceiverFacade numberReceiverFacade,
                                                         WinningNumberGenerable winningNumberGenerable,
                                                         NumbersGeneratorRepository repository,
                                                         NumbersGeneratorFacadeConfigurationProperties properties) {
        return new NumbersGeneratorFacade(numberReceiverFacade, winningNumberGenerable, repository, properties);
    }
}