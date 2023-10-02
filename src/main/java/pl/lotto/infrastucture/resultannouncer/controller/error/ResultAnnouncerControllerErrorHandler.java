package pl.lotto.infrastucture.resultannouncer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lotto.domain.resultchecker.ResultCheckerNotFoundException;

@ControllerAdvice
@Log4j2
public class ResultAnnouncerControllerErrorHandler {

    @ExceptionHandler({ResultCheckerNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultAnnouncerErrorResponseDto handleResultAnnouncerControllerException(ResultCheckerNotFoundException e) {
        log.error(e.getMessage());
        return new ResultAnnouncerErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}