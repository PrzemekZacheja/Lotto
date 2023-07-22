package pl.lotto.domain.numbersgenerator;

import lombok.*;
import pl.lotto.domain.numbersgenerator.dto.*;
import pl.lotto.domain.numbersreceiver.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@AllArgsConstructor
public class NumbersGeneratorFacade {

    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumberGenerable winningNumberGenerator;
    private final NumbersGeneratorRepository numbersGeneratorRepository;
    private final NumbersGeneratorFacadeConfigurationProperties configuration;

    public WinnerNumbersDto generateSixNumbers() {
        LocalDateTime drawDate = numberReceiverFacade.getDrawDate();
        Set<Integer> winningRandomNumbers = winningNumberGenerator.generateWinningRandomNumbers(configuration.lowerBand(),
                configuration.upperBand(),
                configuration.count());
        if (areAllNumbersInRequiredRange(winningRandomNumbers)) {
            WinnerNumbers winnerNumbersDocument = WinnerNumbers.builder()
                                                               .winningNumbers(winningRandomNumbers)
                                                               .drawDate(drawDate)
                                                               .build();
            WinnerNumbers saved = numbersGeneratorRepository.save(winnerNumbersDocument);
            return WinnerNumbersMapper.mapFromWinnerNumbers(saved);
        } else {
            return generateSixNumbers();
        }
    }

    private boolean areAllNumbersInRequiredRange(final Set<Integer> numbers) {
        Set<Integer> limited = limitSetTo(numbers);
        return limited.size() == ConfigNumbersGenerator.RANDOM_NUMBERS;
    }

    private Set<Integer> limitSetTo(Set<Integer> numbers) {
        return numbers.stream()
                      .filter(integer -> integer >= ConfigNumbersGenerator.RANDOM_NUMBER_MIN)
                      .filter(integer -> integer <= ConfigNumbersGenerator.RANDOM_NUMBER_MAX_BOUND)
                      .limit(ConfigNumbersGenerator.RANDOM_NUMBERS)
                      .collect(Collectors.toSet());
    }

    public WinnerNumbersDto retrieveAllWinnerNumbersByNextDrawDate(LocalDateTime localDateTime) {
        WinnerNumbers winnerNumbersByDrawDate = numbersGeneratorRepository.findWinningNumberByDrawDate(localDateTime)
                                                                          .orElseThrow(() -> new WinningNunmbersNotFoundExeption(
                                                                                  "Not Found"));
        return WinnerNumbersDto.builder()
                               .winningNumbers(winnerNumbersByDrawDate.winningNumbers())
                               .timeOfWinDrawNumbers(winnerNumbersByDrawDate.drawDate())
                               .build();
    }
}