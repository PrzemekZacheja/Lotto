package pl.lotto.domain.result_checker;

import lombok.Builder;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;

import java.util.Set;

@Builder
record TicketChecked(
        TicketDto ticketDto,
        boolean isWinner,
        Set<Integer> winnersNumbers,
        String message
) {

}