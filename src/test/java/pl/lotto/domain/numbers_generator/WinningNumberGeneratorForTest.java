package pl.lotto.domain.numbers_generator;

import pl.lotto.domain.numbers_generator.dto.OneNumberGeneratorFetcherDto;

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