package pl.lotto.domain.numbersgenerator;

import java.util.*;

class WinningNumberGeneratorForTest implements WinningNumberGenerable {

    @Override
    public Set<Integer> generateWinningRandomNumbers(int lowerBand, int upperBand, int count) {
        return Set.of(1, 2, 3, 4, 5, 6);
    }
}