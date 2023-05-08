package pl.lotto.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbersgenerator.dto.OneNumberGeneratorFetcherDto;

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
            int intToSet = generateOneNumberFromHttp(RANDOM_NUMBER_MIN, RANDOM_NUMBER_MAX_BOUND).number();
            setToReturn.add(intToSet);
        }
        return setToReturn;
    }

    @Override
    public OneNumberGeneratorFetcherDto generateOneNumberFromHttp(int minBound, int maxBound) {
        int nextInt = secureRandom.nextInt(minBound, maxBound);
        return new OneNumberGeneratorFetcherDto(nextInt);
    }
}