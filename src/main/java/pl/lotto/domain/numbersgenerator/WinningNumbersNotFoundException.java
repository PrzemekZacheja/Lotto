package pl.lotto.domain.numbersgenerator;

public class WinningNumbersNotFoundException extends RuntimeException {

    public WinningNumbersNotFoundException(final String message) {
        super(message);
    }
}