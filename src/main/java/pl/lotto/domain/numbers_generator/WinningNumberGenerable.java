package pl.lotto.domain.numbers_generator;

import java.util.Set;

public interface WinningNumberGenerable extends OneNumberGeneratorFetcher {

    Set<Integer> generateWinningRandomNumbers();
}