package pl.lotto.domain.resultchecker;

import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

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
                        .build())
                .toList();
    }

    static TicketCheckedDto mapToTicketCheckedDto(TicketChecked ticketChecked) {
        return TicketCheckedDto.builder()
                .ticketId(ticketChecked.ticketId())
                .isWinner(ticketChecked.isWinner())
                .message(ticketChecked.message())
                .drawDate(ticketChecked.drawDate())
                .numbersFromUser(ticketChecked.numbersFromUser())
                .winnersNumbers(ticketChecked.winnersNumbers())
                .build();
    }
}