package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.dto.ResultDto;

public class ResultAnnouncerMapper {
    public static ResultAnnouncerResponse mapToResultAnnouncerResponse(ResultDto resultDto) {
        return ResultAnnouncerResponse.builder()
                                      .ticketId(resultDto.ticketId())
                                      .drawDate(resultDto.drawDate())
                                      .winnersNumbers(resultDto.winnersNumbers())
                                      .numbersFromUser(resultDto.numbersFromUser())
                                      .isWinner(resultDto.isWinner())
                                      .message(resultDto.message())
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