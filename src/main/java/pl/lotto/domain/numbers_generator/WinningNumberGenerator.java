package pl.lotto.domain.numbers_generator;

import lombok.AllArgsConstructor;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import static pl.lotto.domain.numbers_generator.ConfigNumbersGenerator.RANDOM_NUMBERS;
import static pl.lotto.domain.numbers_generator.ConfigNumbersGenerator.RANDOM_NUMBER_MAX_BOUND;
import static pl.lotto.domain.numbers_generator.ConfigNumbersGenerator.RANDOM_NUMBER_MIN;

@AllArgsConstructor
class WinningNumberGenerator implements WinningNumberGenerable {

    private final SecureRandom secureRandom;

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
        Set<Integer> setToReturn = new HashSet<>();
        while (setToReturn.size() < RANDOM_NUMBERS) {
            setToReturn.add(secureRandom.nextInt(RANDOM_NUMBER_MIN, RANDOM_NUMBER_MAX_BOUND));
        }
        return setToReturn;
    }
}