package pl.lotto.domain.numbers_generator;

import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;

public class WinnerNumbersMapper

{

    private WinnerNumbersMapper() {
    }

    public static WinnerNumbersDto mapFromWinnerNumbers(WinnerNumbers winnerNumbers) {
        return WinnerNumbersDto.builder()
                .winningNumbers(winnerNumbers.winningNumbers())
                .timeOfWinDrawNumbers(winnerNumbers.timeOfWinDrawNumbers())
                .build();
    }

}