package pl.lotto.domain.numbers_receiver;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(String ticketId, LocalDateTime drawDate, Set<Integer> numbers) {

}