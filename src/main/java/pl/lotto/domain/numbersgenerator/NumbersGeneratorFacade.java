package pl.lotto.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
        WinnerNumbers winnerNumbers = WinnerNumbers.builder()
                                                   .winningNumbers(winningRandomNumbers)
                                                   .timeOfWinDrawNumbers(drawDate)
                                                   .build();
        if (areAllNumbersInRequiredRange(winningRandomNumbers)) {
            numbersGeneratorRepository.save(winnerNumbers);
            return WinnerNumbersMapper.mapFromWinnerNumbers(winnerNumbers);
        } else {
            return generateSixNumbers();
        }
    }

    private boolean areAllNumbersInRequiredRange(final Set<Integer> numbers) {
        Set<Integer> limited = limitSetTo(numbers, ConfigNumbersGenerator.RANDOM_NUMBERS);
        return limited.size() == ConfigNumbersGenerator.RANDOM_NUMBERS;
    }

    private Set<Integer> limitSetTo(Set<Integer> numbers, int limitOfLength) {
        return numbers.stream()
                      .filter(integer -> integer >= 1)
                      .filter(integer -> integer <= 99)
                      .limit(limitOfLength)
                      .collect(Collectors.toSet());
    }

    public WinnerNumbersDto retrieveAllWinnerNumbersByNextDrawDate(LocalDateTime localDateTime) {

        WinnerNumbers winnerNumbersByDrawDate = numbersGeneratorRepository.findWinnerNumbersByDrawDate(localDateTime);
        if (winnerNumbersByDrawDate == null) {
            throw new WinningNunmbersNotFoundExeption("Not Found");
        }
        return WinnerNumbersDto.builder()
                               .winningNumbers(winnerNumbersByDrawDate.winningNumbers())
                               .timeOfWinDrawNumbers(winnerNumbersByDrawDate.timeOfWinDrawNumbers())
                               .build();
    }
}