package pl.lotto.domain.numbersgenerator;

import java.util.Set;

public interface WinningNumberGenerable {

    Set<Integer> generateWinningRandomNumbers(int lowerBand, int upperBand, int count);
}