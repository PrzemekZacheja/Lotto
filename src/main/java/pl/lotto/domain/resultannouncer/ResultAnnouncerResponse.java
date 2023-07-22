package pl.lotto.domain.resultannouncer;

import lombok.*;

import java.time.*;
import java.util.*;

@Builder
record ResultAnnouncerResponse(
        String ticketId,
        LocalDateTime drawDate,
        Set<Integer> numbersFromUser,
        Set<Integer> winnersNumbers,
        boolean isWinner,
        String message
) {
}