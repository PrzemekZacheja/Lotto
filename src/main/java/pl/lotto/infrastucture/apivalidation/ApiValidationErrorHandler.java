package pl.lotto.infrastucture.apivalidation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ApiValidationErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiValidationErrorDto handleApiValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getAllErrors()
                               .stream()
                               .map(x -> x.getDefaultMessage())
                               .toList();
        return new ApiValidationErrorDto(errors, HttpStatus.BAD_REQUEST);
    }
}