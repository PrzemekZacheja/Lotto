package pl.lotto.domain.numbersgenerator;

import java.util.Set;

public interface WinningNumberGenerable extends OneNumberGeneratorFetcher {

    Set<Integer> generateWinningRandomNumbers();
}