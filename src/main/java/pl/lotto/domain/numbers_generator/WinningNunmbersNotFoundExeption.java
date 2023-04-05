package pl.lotto.domain.numbers_generator;

public class WinningNunmbersNotFoundExeption extends RuntimeException {

    public WinningNunmbersNotFoundExeption(final String message) {
        super(message);
    }
}