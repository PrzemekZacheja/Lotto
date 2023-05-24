package pl.lotto.domain.numbersgenerator;

import java.util.Set;

class WinningNumberGeneratorForTest implements WinningNumberGenerable {

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
        return Set.of(1, 2, 3, 4, 5, 6);
    }
}