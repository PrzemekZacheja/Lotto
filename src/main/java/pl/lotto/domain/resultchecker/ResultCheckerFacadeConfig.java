package pl.lotto.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

@Configuration
public class ResultCheckerFacadeConfig {

    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade, DrawDateFacade drawDateFacade,
                                                   ResultRepository resultRepository, ResultChecker resultChecker) {
        return new ResultCheckerFacade(numberReceiverFacade, drawDateFacade, resultRepository,
                                       resultChecker);
    }

    @Bean
    public ResultChecker resultChecker(ResultRepository resultRepository, NumbersGeneratorFacade numbersGeneratorFacade) {
        return new ResultChecker(resultRepository, numbersGeneratorFacade);
    }
}