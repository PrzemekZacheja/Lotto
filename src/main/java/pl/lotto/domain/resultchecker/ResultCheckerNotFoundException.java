package pl.lotto.domain.resultchecker;

public class ResultCheckerNotFoundException extends RuntimeException {

    public ResultCheckerNotFoundException(String message) {
        super(message);
    }
}