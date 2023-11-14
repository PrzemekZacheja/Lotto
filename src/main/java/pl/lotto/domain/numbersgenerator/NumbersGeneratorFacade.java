package pl.lotto.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.Optional;
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
            return WinnerNumbersMapper.mapFromWinnerNumbers(byDrawDate.get());
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
                                                                          .orElseThrow(() -> new WinningNumbersFoundException(
                                                                                  "Not Found"));
        return WinnerNumbersDto.builder()
                               .winningNumbers(winnerNumbersByDrawDate.winningNumbers())
                               .timeOfWinDrawNumbers(winnerNumbersByDrawDate.drawDate())
                               .build();
    }

    public boolean areWinnerNumbersGenerated() {
        WinnerNumbersDto winnerNumbersDto = retrieveAllWinnerNumbersByNextDrawDate(numberReceiverFacade.getDrawDate());
        return !winnerNumbersDto.winningNumbers()
                                .isEmpty();
    }
}