package pl.lotto.domain.numbers_generator;

import java.util.Set;

public class WinningNumberGeneratorForTest implements WinningNumberGenerable {

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
        return Set.of(1,2,3,4,5,6);
    }
}