package pl.lotto.infrastucture.resultchecker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersgenerator.WinningNunmbersNotFoundExeption;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

@Log4j2
@Component
@AllArgsConstructor
public class ResultCheckerScheduler {

    ResultCheckerFacade resultCheckerFacade;
    NumbersGeneratorFacade numbersGeneratorFacade;

    @Scheduled(cron = "${lotto.result-checker.lotteryRunOccurrence}")
    public void checkResults() {
        log.info("Checking results");
        if (!numbersGeneratorFacade.areWinnerNumbersGenerated()) {
            log.error("Winner numbers are not generated");
            throw new WinningNunmbersNotFoundExeption("Winner numbers are not generated");
        }
        log.info("Generating results");
        resultCheckerFacade.generateResultsOfTickets();
    }
}