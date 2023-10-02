package pl.lotto.domain.resultannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

@Configuration
public class ResultAnnouncerConfig {

    @Bean
    public ResultAnnouncerFacade resultAnnouncerFacade(ResultCheckerFacade resultCheckerFacade,
                                                       ResultAnnouncerResponseRepository repository) {
        return new ResultAnnouncerFacade(resultCheckerFacade, repository);
    }
}