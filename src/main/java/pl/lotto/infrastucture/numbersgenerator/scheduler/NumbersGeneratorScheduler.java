package pl.lotto.infrastucture.numbersgenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;

@Component
@AllArgsConstructor
@Log4j2
public class NumbersGeneratorScheduler {

    private final NumbersGeneratorFacade numbersGeneratorFacade;

    @Scheduled(cron = "${lotto.number-generator.lotteryRunOccurrence}")
    public void scheduleGenerateSixNumbers() {
        log.info("Scheduling scheduleGenerateSixNumbers()");
        WinnerNumbersDto winnerNumbersDto = numbersGeneratorFacade.generateSixNumbers();
        log.info(winnerNumbersDto.winningNumbers());
        log.info(winnerNumbersDto.timeOfWinDrawNumbers());
    }
}