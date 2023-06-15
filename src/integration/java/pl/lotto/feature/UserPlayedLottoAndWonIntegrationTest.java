package pl.lotto.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersgenerator.WinningNunmbersNotFoundExeption;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.awaitility.Awaitility.await;

class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    NumbersGeneratorFacade numbersGeneratorFacade;


    @Test
    void should_user_win_and_system_should_generate_winner() {
//        step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody(
                                                                   "[43, 5, 80, 14, 62, 31, 99, 51, 22, 40, 4, 75, 71, 31, 30, 66, 64, 53, 78, 72, 68, 4, 62, 70, 84]")));
        //when
        numbersGeneratorFacade.generateSixNumbers();


//        step 2: system fetched winning numbers for draw date: 19.11.2022 10:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 11, 26, 12, 0, 0);
        //when & then
        await().atMost(Duration.ofSeconds(20))
               .pollInterval(Duration.ofSeconds(1))
               .until(() -> {
                   try {
                       return !numbersGeneratorFacade.retrieveAllWinnerNumbersByNextDrawDate(drawDate)
                                                     .winningNumbers()
                                                     .isEmpty();

                   } catch (WinningNunmbersNotFoundExeption exception) {
                       return false;
                   }
               });


//        step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
//        step 4: user made GET /results/notExistingId and system returned 404(NOT_FOUND) and body with (“message”: “Not found for id: notExistingId” and “status”: “NOT_FOUND”)
//        step 5: 3 days and 55 minutes passed, and it is 5 minute before draw (19.11.2022 11:55)
//        step 6: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
//        step 7: 6 minutes passed, and it is 1 minute after the draw (19.11.2022 12:01)
//        step 8: user made GET /results/sampleTicketId and system returned 200 (OK)

    }
}