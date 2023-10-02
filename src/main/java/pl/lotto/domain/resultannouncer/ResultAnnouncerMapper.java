package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

public class ResultAnnouncerMapper {
    public static ResultAnnouncerResponse mapToResultAnnouncerResponse(TicketCheckedDto ticketCheckedDto) {
        return ResultAnnouncerResponse.builder()
                                      .ticketId(ticketCheckedDto.ticketId())
                                      .drawDate(ticketCheckedDto.drawDate())
                                      .winnersNumbers(ticketCheckedDto.winnersNumbers())
                                      .numbersFromUser(ticketCheckedDto.numbersFromUser())
                                      .isWinner(ticketCheckedDto.isWinner())
                                      .message(ticketCheckedDto.message())
                                      .build();
    }

    public static ResultAnnouncerResponseDto mapToResultAnnouncerResponseDto(ResultAnnouncerResponse resultResponse) {
        return ResultAnnouncerResponseDto.builder()
                                         .responseDto(ResponseDto.builder()
                                                                 .isWinner(resultResponse.isWinner())
                                                                 .ticketId(resultResponse.ticketId())
                                                                 .numbersFromUser(resultResponse.numbersFromUser())
                                                                 .winnersNumbers(resultResponse.winnersNumbers())
                                                                 .drawDate(resultResponse.drawDate())
                                                                 .build())
                                         .message(resultResponse.message())
                                         .build();
    }
}