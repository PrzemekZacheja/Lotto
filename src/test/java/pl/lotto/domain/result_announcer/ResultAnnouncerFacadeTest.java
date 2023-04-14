package pl.lotto.domain.result_announcer;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.result_announcer.dto.ResultAnnouncerResponseDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ResultAnnouncerFacadeTest {

    @Test
    void should_return_correct_ResponseDto_object() {
        //given
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade();
        //when
        ResultAnnouncerResponseDto responseDto = resultAnnouncerFacade.generateResponse();
        //then
        assertThat(responseDto).isEqualTo(ResultAnnouncerResponseDto.builder()

                .message("You win")
                .build());
    }

    @Test
    void should_
}