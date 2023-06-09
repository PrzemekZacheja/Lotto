package pl.lotto.domain.numbersgenerator;

import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;

public class WinnerNumbersMapper {

    private WinnerNumbersMapper() {
    }

    public static WinnerNumbersDto mapFromWinnerNumbers(WinnerNumbers winnerNumbers) {
        return WinnerNumbersDto.builder()
                .winningNumbers(winnerNumbers.winningNumbers())
                .timeOfWinDrawNumbers(winnerNumbers.timeOfWinDrawNumbers())
                .build();
    }

}