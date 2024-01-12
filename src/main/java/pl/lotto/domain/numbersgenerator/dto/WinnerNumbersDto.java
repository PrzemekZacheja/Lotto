package pl.lotto.domain.numbersgenerator.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record WinnerNumbersDto(LocalDateTime timeOfWinDrawNumbers,
                               Set<Integer> winningNumbers) {

}