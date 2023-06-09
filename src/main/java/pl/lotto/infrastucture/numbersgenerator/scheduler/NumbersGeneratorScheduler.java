package pl.lotto.infrastucture.numbersgenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;

@Component
@AllArgsConstructor
@Log4j2
public class NumbersGeneratorScheduler {

    private final NumbersGeneratorFacade numbersGeneratorFacade;

    @Scheduled(cron = "*/10 * * * * *")
    public void scheduleGenerateSixNumbers() {
        log.info("Scheduling");
        numbersGeneratorFacade.generateSixNumbers();
    }
}