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
        if (areAllNumbersInRequiredRange(winningRandomNumbers)) {
            WinnerNumbers winnerNumbersSavedToDB = numbersGeneratorRepository.save(
                    WinnerNumbers.builder()
                            .winningNumbers(winningRandomNumbers)
                            .timeOfWinDrawNumbers(drawDate)
                            .build()
            );
            return WinnerNumbersMapper.mapFromWinnerNumbers(winnerNumbersSavedToDB);
        } else {
            throw new IllegalStateException("set of numbers" + winningRandomNumbers);
        }
    }

    private boolean areAllNumbersInRequiredRange(final Set<Integer> numbers) {
        long sizeOfNumbersInRange = numbers.stream()
                .filter(integer -> integer >= 1)
                .filter(integer -> integer <= 99)
                .count();
        return sizeOfNumbersInRange == 6;
    }

    public WinnerNumbersDto retrieveAllWinnerNumbersByNextDrawDate(LocalDateTime localDateTime) {

        WinnerNumbers winnerNumbersByDrawDate =
                numbersGeneratorRepository.findWinnerNumbersByDrawDate(localDateTime);
        if (winnerNumbersByDrawDate == null) {
            throw new WinningNunmbersNotFoundExeption("Not Found");
        }
        return WinnerNumbersDto.builder()
                .winningNumbers(winnerNumbersByDrawDate.winningNumbers())
                .timeOfWinDrawNumbers(winnerNumbersByDrawDate.timeOfWinDrawNumbers())
                .build();
    }
}