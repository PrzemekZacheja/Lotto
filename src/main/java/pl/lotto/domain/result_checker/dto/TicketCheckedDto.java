package pl.lotto.domain.result_checker.dto;

import lombok.Builder;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;

import java.util.Set;

@Builder
public
record TicketCheckedDto(TicketDto ticketDto, boolean isWinner, Set<Integer> winnersNumbers, String message) {

}