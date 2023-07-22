package pl.lotto.domain.numbersgenerator.dto;

import lombok.*;

import java.time.*;
import java.util.*;

@Builder
public record WinnerNumbersDto(LocalDateTime timeOfWinDrawNumbers, Set<Integer> winningNumbers) {

}