package pl.lotto.domain.numbers_generator;

import lombok.AllArgsConstructor;

import java.security.SecureRandom;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.lotto.domain.numbers_generator.ConfigNumbersGenerator.RANDOM_NUMBERS;
import static pl.lotto.domain.numbers_generator.ConfigNumbersGenerator.RANDOM_NUMBER_MAX_BOUND;
import static pl.lotto.domain.numbers_generator.ConfigNumbersGenerator.RANDOM_NUMBER_MIN;

@AllArgsConstructor
class WinningNumberGenerator implements WinningNumberGenerable {

    private final SecureRandom secureRandom;

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
        return secureRandom.ints(RANDOM_NUMBERS, RANDOM_NUMBER_MIN, RANDOM_NUMBER_MAX_BOUND).boxed().collect(Collectors.toSet());
    }
}