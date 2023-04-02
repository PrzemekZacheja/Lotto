package pl.lotto.domain.numbers_generator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbers_receiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class NumbersGeneratorFacade {

    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumberGenerable winningNumberGenerator;
    private final NumbersGeneratorRepository numbersGeneratorRepository;

    public WinnerNumbersDto generateSixNumbers() {
        LocalDateTime drawDate = numberReceiverFacade.getDrawDate();
        Set<Integer> winningRandomNumbers = winningNumberGenerator.generateWinningRandomNumbers();

        WinnerNumbers winnerNumbersSavedToDB = numbersGeneratorRepository.save(
                WinnerNumbers.builder()
                        .winningNumbers(winningRandomNumbers)
                        .timeOfWinDrawNumbers(drawDate)
                        .build()
        );
        return new WinnerNumbersDto(drawDate, winningRandomNumbers);
    }
}