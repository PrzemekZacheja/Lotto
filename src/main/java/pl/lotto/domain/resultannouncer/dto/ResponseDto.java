package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResponseDto(String ticketId,
                          LocalDateTime drawDate,
                          Set<Integer> numbersFromUser,
                          Set<Integer> winnersNumbers,
                          boolean isWinner) {
}