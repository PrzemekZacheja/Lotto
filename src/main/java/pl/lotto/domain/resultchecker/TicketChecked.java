package pl.lotto.domain.resultchecker;

import lombok.*;

import java.time.*;
import java.util.*;

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