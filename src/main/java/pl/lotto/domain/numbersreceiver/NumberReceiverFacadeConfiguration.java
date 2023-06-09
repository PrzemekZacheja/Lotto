package pl.lotto.domain.numbersreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Configuration
public class NumberReceiverFacadeConfiguration {

    @Bean
    NumberValidator numberValidator() {
        return new NumberValidator();
    }

    @Bean
    NumberReceiverRepository numberReceiverRepository() {
        return new NumberReceiverRepository() {
            @Override
            public Ticket save(Ticket ticket) {
                return null;
            }

            @Override
            public List<Ticket> findAllTicketsByDrawDate(LocalDateTime dateOfDraw) {
                return Collections.emptyList();
            }
        };
    }

    @Bean
    HashGenerable hashGenerable() {
        return new HashGenerator();
    }

    @Bean
    public NumberReceiverFacade numberReceiverFacade(NumberValidator numberValidator, NumberReceiverRepository numberRepository, HashGenerable hashGenerable, DrawDateFacade drawDateFacade) {
        return new NumberReceiverFacade(numberValidator, numberRepository, hashGenerable, drawDateFacade);
    }
}