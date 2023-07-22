package pl.lotto.domain.numbersgenerator;

import java.util.*;

public interface WinningNumberGenerable {

    Set<Integer> generateWinningRandomNumbers(int lowerBand, int upperBand, int count);
}