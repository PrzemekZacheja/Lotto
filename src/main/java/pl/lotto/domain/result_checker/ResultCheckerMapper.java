package pl.lotto.domain.result_checker;

import pl.lotto.domain.result_checker.dto.TicketCheckedDto;

import java.util.List;

class ResultCheckerMapper {

    private ResultCheckerMapper() {
    }

    static List<TicketChecked> mapToTicketChecked(final List<TicketCheckedDto> ticketCheckedDtoList) {
        return ticketCheckedDtoList.stream()
                .map(ticketCheckedDto -> TicketChecked.builder()
                        .ticketId(ticketCheckedDto.ticketId())
                        .numbersFromUser(ticketCheckedDto.numbersFromUser())
                        .drawDate(ticketCheckedDto.drawDate())
                        .winnersNumbers(ticketCheckedDto.winnersNumbers())
                        .isWinner(ticketCheckedDto.isWinner())
                        .message(ticketCheckedDto.message())
                        .build()).toList();
    }
}