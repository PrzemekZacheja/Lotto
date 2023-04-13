package pl.lotto.domain.result_checker;

import pl.lotto.domain.result_checker.dto.TicketCheckedDto;

import java.util.List;

class ResultCheckerMapper {

    private ResultCheckerMapper() {
    }

    static List<TicketChecked> mapToTicketChecked(final List<TicketCheckedDto> ticketCheckedDtoList) {
        return ticketCheckedDtoList.stream()
                .map(ticketCheckedDto -> TicketChecked.builder()
                        .ticketDto(ticketCheckedDto.ticketDto())
                        .winnersNumbers(ticketCheckedDto.winnersNumbers())
                        .isWinner(ticketCheckedDto.isWinner())
                        .message(ticketCheckedDto.message())
                        .build()).toList();
    }
}