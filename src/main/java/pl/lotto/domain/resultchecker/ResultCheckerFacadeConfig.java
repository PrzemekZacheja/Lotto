package pl.lotto.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

@Configuration
public class ResultCheckerFacadeConfig {

    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumbersGeneratorFacade numbersGeneratorFacade,
                                                   NumberReceiverFacade numberReceiverFacade, DrawDateFacade drawDateFacade,
                                                   TicketCheckedRepository ticketCheckedRepository, ResultChecker resultChecker) {
        return new ResultCheckerFacade(numbersGeneratorFacade, numberReceiverFacade, drawDateFacade, ticketCheckedRepository,
                                       resultChecker);
    }

    @Bean
    public ResultChecker resultChecker(TicketCheckedRepository ticketCheckedRepository) {
        return new ResultChecker(ticketCheckedRepository);
    }
}