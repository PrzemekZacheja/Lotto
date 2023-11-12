package pl.lotto.domain.resultchecker;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record Result(
        @Id
        String ticketId,
        LocalDateTime drawDate,
        Set<Integer> numbersFromUser,
        boolean isWinner,
        Set<Integer> winnersNumbers,
        String message
) {

}