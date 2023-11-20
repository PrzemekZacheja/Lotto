package pl.lotto.domain.numbersreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;

@Configuration
public class NumberReceiverFacadeConfiguration {

    @Bean
    NumberValidator numberValidator() {
        return new NumberValidator();
    }

    @Bean
    HashGenerable hashGenerable() {
        return new HashGenerator();
    }

    @Bean
    public NumberReceiverFacade numberReceiverFacade(
            NumberValidator numberValidator,
            NumberReceiverRepository numberRepository,
            HashGenerable hashGenerable,
            DrawDateFacade drawDateFacade
                                                    ) {
        return new NumberReceiverFacade(numberValidator, numberRepository, hashGenerable, drawDateFacade);
    }
}