package pl.lotto.domain.result_announcer;

import pl.lotto.domain.result_announcer.dto.ResultAnnouncerResponseDto;

public class ResultAnnouncerFacade {

    public ResultAnnouncerResponseDto generateResponse() {
        return ResultAnnouncerResponseDto.builder()
                .message("You win")
                .build();
    }
}