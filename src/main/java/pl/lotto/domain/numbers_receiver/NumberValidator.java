package pl.lotto.domain.numbers_receiver;

import java.util.Set;

class NumberValidator {

    private static final int MAX_NUMBERS_IN_GAME = 6;
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 99;

    boolean areAllNumbersInRange(final Set<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number >= MIN_RANGE)
                .filter(number -> number <= MAX_RANGE)
                .count() == MAX_NUMBERS_IN_GAME;
    }
}