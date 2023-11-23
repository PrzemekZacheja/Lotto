package pl.lotto.http.numbergenerator;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;
import wiremock.org.apache.hc.core5.http.HttpStatus;

import java.util.List;
import java.util.Set;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class WinningNumberGeneratorRestTemplateIntegrationTest {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
                                                                      .options(wireMockConfig().dynamicPort())
                                                                      .build();

    WinningNumberGenerable winningNumberGenerator = new WinningNumberGeneratorRestTemplateIntegrationTestConfig().remoteWinningNumberGeneratorClient(
            wireMockServer.getPort(), 1000, 1000);

    @Test
    void should_return_200_and_winning_numbers() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.SC_OK)
                                                           .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                                                           .withBody(
                                                                   "[1, 2, 3, 4, 5, 6, 43, 5, 80, 14, 62, 31, 99, 51, 22, 40, " +
                                                                           "4, 75, 71, 31, 30, 66, 64, 53, 78, 72, 68, 4, 62, 70, 84]".trim()
                                                                    )
                                                  ));
        //when
        Set<Integer> winningRandomNumbers = winningNumberGenerator.generateWinningRandomNumbers(1, 99, 25);
        //then
        assertThat(winningRandomNumbers).containsExactlyInAnyOrderElementsOf(List.of(1, 2, 3, 4, 5, 6));
    }
}