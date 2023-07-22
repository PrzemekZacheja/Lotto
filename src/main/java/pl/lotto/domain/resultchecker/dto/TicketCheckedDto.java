package pl.lotto.domain.resultchecker.dto;

import lombok.*;

import java.time.*;
import java.util.*;

@Builder
public record TicketCheckedDto(
        String ticketId,
        LocalDateTime drawDate,
        Set<Integer> numbersFromUser,
        Set<Integer> winnersNumbers,
        boolean isWinner,
        String message
) {

}