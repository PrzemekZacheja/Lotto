package pl.lotto.http.numbergenerator;

import org.springframework.web.client.RestTemplate;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;
import pl.lotto.infrastucture.numbersgenerator.http.WinningNumberGenerationClientConfig;
import pl.lotto.infrastucture.numbersgenerator.http.WinningNumberGeneratorRestTemplate;

public class WinningNumberGeneratorRestTemplateIntegrationTestConfig extends WinningNumberGenerationClientConfig {

    public WinningNumberGenerable remoteWinningNumberGeneratorClient(int port, int connectionTimeout, int readTimeout) {
        RestTemplate restTemplate = restTemplate(restTemplateResponseErrorHandler(), connectionTimeout, readTimeout);
        return new WinningNumberGeneratorRestTemplate(restTemplate, "http://localhost", port);
    }
}