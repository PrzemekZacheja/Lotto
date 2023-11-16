package pl.lotto.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NumbersGeneratorFacade {

    private final WinningNumberGenerable winningNumberGenerator;
    private final NumbersGeneratorRepository numbersGeneratorRepository;
    private final NumbersGeneratorFacadeConfigurationProperties configuration;
    private final DrawDateFacade drawDateFacade;

    public WinnerNumbersDto generateSixNumbers() {
        LocalDateTime drawDate = drawDateFacade.generateDateOfNextDraw();
        Set<Integer> winningRandomNumbers = winningNumberGenerator.generateWinningRandomNumbers(configuration.lowerBand(),
                configuration.upperBand(),
                configuration.count());
        if (areAllNumbersInRequiredRange(winningRandomNumbers)) {
            WinnerNumbers winnerNumbersDocument = WinnerNumbers.builder()
                                                               .winningNumbers(winningRandomNumbers)
                                                               .drawDate(drawDate)
                                                               .build();
            return getWinnerNumbersDto(drawDate, winnerNumbersDocument);
        } else {
            return generateSixNumbers();
        }
    }

    private WinnerNumbersDto getWinnerNumbersDto(LocalDateTime drawDate, WinnerNumbers winnerNumbersDocument) {
        if (!numbersGeneratorRepository.existsByDrawDate(drawDate)) {
            WinnerNumbers saved = numbersGeneratorRepository.save(winnerNumbersDocument);
            return WinnerNumbersMapper.mapFromWinnerNumbers(saved);
        } else {
            Optional<WinnerNumbers> byDrawDate = numbersGeneratorRepository.findByDrawDate(drawDate);
            WinnerNumbers winnerNumbers = byDrawDate.get();
            return WinnerNumbersMapper.mapFromWinnerNumbers(winnerNumbers);
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
        WinnerNumbers winnerNumbersByDrawDate = numbersGeneratorRepository.findByDrawDate(localDateTime)
                                                                          .orElseThrow(() -> new WinningNumbersNotFoundException(
                                                                                  "Not Found"));
        return WinnerNumbersDto.builder()
                               .winningNumbers(winnerNumbersByDrawDate.winningNumbers())
                               .timeOfWinDrawNumbers(winnerNumbersByDrawDate.drawDate())
                               .build();
    }
}