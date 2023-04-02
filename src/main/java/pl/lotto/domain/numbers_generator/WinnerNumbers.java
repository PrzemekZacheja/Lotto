package pl.lotto.domain.numbers_generator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record WinnerNumbers(LocalDateTime timeOfWinDrawNumbers, Set<Integer> winningNumbers) {

}