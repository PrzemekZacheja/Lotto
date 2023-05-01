package pl.lotto.domain.numbers_generator;

import pl.lotto.domain.numbers_generator.dto.OneNumberGeneratorFetcherDto;

public interface OneNumberGeneratorFetcher {

    OneNumberGeneratorFetcherDto generateOneNumberFromHttp(int minBound, int maxBound);
}