package pl.lotto.infrastucture.resultchecker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

@Log4j2
@Component
@AllArgsConstructor
public class ResultCheckerScheduler {

    ResultCheckerFacade resultCheckerFacade;
    NumbersGeneratorFacade numbersGeneratorFacade;

    @Scheduled(cron = "${lotto.resultchecker.lotteryRunOccurrence}")
    public void checkResults() {
        log.info("Generating results");
        resultCheckerFacade.generateResultsOfTickets();
    }
}