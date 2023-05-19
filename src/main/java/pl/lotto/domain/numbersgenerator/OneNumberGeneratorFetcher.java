package pl.lotto.domain.numbersgenerator;

import pl.lotto.domain.numbersgenerator.dto.OneNumberGeneratorFetcherDto;

public interface OneNumberGeneratorFetcher {

    OneNumberGeneratorFetcherDto generateOneNumberFromHttp(int minBound, int maxBound);
}