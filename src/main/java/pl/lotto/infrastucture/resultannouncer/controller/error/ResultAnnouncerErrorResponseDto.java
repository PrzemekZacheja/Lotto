package pl.lotto.infrastucture.resultannouncer.controller.error;

import org.springframework.http.HttpStatus;

public record ResultAnnouncerErrorResponseDto(String message,
                                              HttpStatus status) {
}