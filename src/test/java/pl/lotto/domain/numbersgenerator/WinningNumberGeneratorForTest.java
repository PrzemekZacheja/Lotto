package pl.lotto.domain.numbersgenerator;

import pl.lotto.domain.numbersgenerator.dto.OneNumberGeneratorFetcherDto;

import java.util.Set;

class WinningNumberGeneratorForTest implements WinningNumberGenerable {

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
        return Set.of(1, 2, 3, 4, 5, 6);
    }

    @Override
    public OneNumberGeneratorFetcherDto generateOneNumberFromHttp(int minBound, int maxBound) {
        return new OneNumberGeneratorFetcherDto(0);
    }
}