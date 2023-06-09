package pl.lotto.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

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