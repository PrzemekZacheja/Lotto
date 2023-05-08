package pl.lotto.domain.numbersgenerator;

public class WinningNunmbersNotFoundExeption extends RuntimeException {

    public WinningNunmbersNotFoundExeption(final String message) {
        super(message);
    }
}