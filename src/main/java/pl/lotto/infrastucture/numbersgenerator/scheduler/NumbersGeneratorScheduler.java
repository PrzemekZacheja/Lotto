package pl.lotto.infrastucture.numbersgenerator.scheduler;

import lombok.*;
import lombok.extern.log4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import pl.lotto.domain.numbersgenerator.*;
import pl.lotto.domain.numbersgenerator.dto.*;

@Component
@AllArgsConstructor
@Log4j2
public class NumbersGeneratorScheduler {

    private final NumbersGeneratorFacade numbersGeneratorFacade;

    @Scheduled(cron = "${lotto.numbergenerator.lotteryRunOccurrence}")
    public void scheduleGenerateSixNumbers() {
        log.info("Scheduling");
        WinnerNumbersDto winnerNumbersDto = numbersGeneratorFacade.generateSixNumbers();
        log.info(winnerNumbersDto.winningNumbers());
        log.info(winnerNumbersDto.timeOfWinDrawNumbers());
    }
}