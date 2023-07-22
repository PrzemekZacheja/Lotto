package pl.lotto.domain.resultannouncer.dto;

import lombok.*;

import java.time.*;
import java.util.*;

@Builder
public record ResultAnnouncerResponseDto(
        String ticketId,
        LocalDateTime drawDate,
        Set<Integer> numbersFromUser,
        Set<Integer> winnersNumbers,
        boolean isWinner,
        String message
) {
}