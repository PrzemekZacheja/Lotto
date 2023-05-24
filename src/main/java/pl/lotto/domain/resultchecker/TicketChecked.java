package pl.lotto.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record TicketChecked(
        String ticketId,
        LocalDateTime drawDate,
        Set<Integer> numbersFromUser,
        boolean isWinner,
        Set<Integer> winnersNumbers,
        String message
) {

}