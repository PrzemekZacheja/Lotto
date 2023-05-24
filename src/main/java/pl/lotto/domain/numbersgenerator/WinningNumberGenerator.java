package pl.lotto.domain.numbersgenerator;

import lombok.AllArgsConstructor;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import static pl.lotto.domain.numbersgenerator.ConfigNumbersGenerator.*;

@AllArgsConstructor
class WinningNumberGenerator implements WinningNumberGenerable {

    private final SecureRandom secureRandom;

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
        Set<Integer> setToReturn = new HashSet<>();
        while (setToReturn.size() < RANDOM_NUMBERS) {
            int nextInt = secureRandom.nextInt(RANDOM_NUMBER_MIN, RANDOM_NUMBER_MAX_BOUND);
            setToReturn.add(nextInt);
        }
        return setToReturn;
    }
}