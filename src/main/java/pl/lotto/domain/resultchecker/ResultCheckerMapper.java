package pl.lotto.domain.resultchecker;

import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.util.List;

class ResultCheckerMapper {

    private ResultCheckerMapper() {
    }

    static List<Result> mapToResult(final List<ResultDto> resultDtoList) {
        return resultDtoList.stream()
                            .map(ticketCheckedDto -> Result.builder()
                                                           .ticketId(ticketCheckedDto.ticketId())
                                                           .numbersFromUser(ticketCheckedDto.numbersFromUser())
                                                           .drawDate(ticketCheckedDto.drawDate())
                                                           .winnersNumbers(ticketCheckedDto.winnersNumbers())
                                                           .isWinner(ticketCheckedDto.isWinner())
                                                           .message(ticketCheckedDto.message())
                                                           .build())
                            .toList();
    }

    static ResultDto mapToTicketCheckedDto(Result ticketChecked) {
        return ResultDto.builder()
                        .ticketId(ticketChecked.ticketId())
                        .isWinner(ticketChecked.isWinner())
                        .message(ticketChecked.message())
                        .drawDate(ticketChecked.drawDate())
                        .numbersFromUser(ticketChecked.numbersFromUser())
                        .winnersNumbers(ticketChecked.winnersNumbers())
                        .build();
    }
}