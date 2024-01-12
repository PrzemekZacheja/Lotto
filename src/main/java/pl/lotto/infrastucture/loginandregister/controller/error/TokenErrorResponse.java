package pl.lotto.infrastucture.loginandregister.controller.error;

import org.springframework.http.HttpStatus;

public record TokenErrorResponse(String message,
                                 HttpStatus error) {
}