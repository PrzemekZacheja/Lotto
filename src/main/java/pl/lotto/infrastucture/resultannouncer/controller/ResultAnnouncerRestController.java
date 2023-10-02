package pl.lotto.infrastucture.resultannouncer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;

@RestController
@Log4j2
@AllArgsConstructor
public class ResultAnnouncerRestController {

    ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/results/{ticketId}")
    public ResponseEntity<ResultAnnouncerResponseDto> announceResults(@PathVariable String ticketId) {
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.
                generateResponseByIdTicket(ticketId);
        return ResponseEntity.ok(resultAnnouncerResponseDto);
    }
}