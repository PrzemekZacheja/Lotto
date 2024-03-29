package pl.lotto.featurehappypath;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersgenerator.WinningNumbersNotFoundException;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.ResultCheckerNotFoundException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    NumbersGeneratorFacade numbersGeneratorFacade;
    @Autowired
    ResultCheckerFacade resultCheckerFacade;
    @Autowired
    AdjustableClock clock;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("lotto.numbergenerator.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("lotto.numbergenerator.http.client.config.port", () -> wireMockServer.getPort());
    }


    @Test
    @WithMockUser
    void should_user_win_and_system_should_generate_winner() throws Exception {
//        step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                                       .willReturn(WireMock.aResponse()
                                                           .withStatus(HttpStatus.OK.value())
                                                           .withHeader("Content-Type", "application/json")
                                                           .withBody(
                                                                   "[1, 2, 3, 4, 5, 6, 43, 5, 80, 14, 62, 31, 99, 51, 22, 40, " +
                                                                           "4, 75, 71, 31, 30, 66, 64, 53, 78, 72, 68, 4, 62, 70, 84]")));
        //when
        numbersGeneratorFacade.generateSixNumbers();


//        step 2: system fetched winning numbers for draw date: 19.11.2022 12:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 11, 19, 12, 0, 0);
        //when & then
        await().atMost(Duration.ofSeconds(20))
               .pollInterval(Duration.ofSeconds(1))
               .until(() -> {
                   try {
                       return !numbersGeneratorFacade.retrieveAllWinnerNumbersByDrawDate(drawDate)
                                                     .winningNumbers()
                                                     .isEmpty();

                   } catch (WinningNumbersNotFoundException exception) {
                       return false;
                   }
               });


//          4. user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned
//          OK(200) with
//        message:
//        “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
        //given
        //when
        ResultActions performPostInputNumbers = mockMvc.perform(post("/inputNumbers").content("""
                                                                                              {
                                                                                              "inputNumbers" : [1, 2, 3, 4, 5, 6]
                                                                                              }
                                                                                              """.trim())
                                                                                     .contentType(
                                                                                             MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResultInStepSix = performPostInputNumbers.andExpect(status().isOk())
                                                              .andReturn();
        String contentAsString = mvcResultInStepSix.getResponse()
                                                   .getContentAsString();
        TicketDto ticketDto = objectMapper.readValue(contentAsString, TicketDto.class);
        String ticketId = ticketDto.ticketId();

        assertAll(
                () -> assertThat(ticketDto.drawDate()).isEqualTo(drawDate),
                () -> assertThat(ticketId).isNotNull(),
                () -> assertThat(ticketDto.message()).isEqualTo("Success")
                 );


//        5. user made GET /results/notExistingId and system returned 404(NOT_FOUND)
//          and body with (“message”: “Not found for id: notExistingId” and “status”: “NOT_FOUND”)
        //given
        //when
        ResultActions performGetResultWithNoExistingId = mockMvc.perform(get("/results/notExistingId"));
        //then
        performGetResultWithNoExistingId.andExpect(status().isNotFound())
                                        .andExpect(content().json("""
                                                                    {
                                                                    "message": "Not found for id: notExistingId",
                                                                    "status" : "NOT_FOUND"
                                                                    }
                                                                  """

                                                                 ));


//        step 6: 3 days and 55 minutes passed, and it is 5 minute before draw (19.11.2022 11:55)
        //given & when & then
        clock.plusDaysAndMinutes(3, 55);

//        step 7: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it
//        with 6 hits
        await().atMost(20, TimeUnit.SECONDS)
               .pollInterval(Duration.ofSeconds(1L))
               .until(() -> {
                   try {
                       return !resultCheckerFacade.retrieveTicketCheckedByIdTicket(ticketId)
                                                  .winnersNumbers()
                                                  .isEmpty();
                   } catch (ResultCheckerNotFoundException exception) {
                       log.error("Step 6 exception; " + exception);
                       return false;
                   }
               });


//        step 8: 6 minutes passed, and it is 1 minute after the draw (19.11.2022 12:01)
        //given & when & then
        clock.plusDaysAndMinutes(0, 6);


//        step 9: user made GET /results/sampleTicketId and system returned 200 (OK)
        //given & when
        ResultActions performGetResultWithExistingId = mockMvc.perform(get("/results/" + ticketId));
        //then
        String content = performGetResultWithExistingId.andExpect(status().isOk())
                                                       .andReturn()
                                                       .getResponse()
                                                       .getContentAsString();
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = objectMapper.readValue(content, ResultAnnouncerResponseDto.class);
        assertAll(
                () -> assertThat(resultAnnouncerResponseDto.responseDto()
                                                           .ticketId()).isEqualTo(ticketId),
                () -> assertThat(resultAnnouncerResponseDto.responseDto()
                                                           .drawDate()).isEqualTo(drawDate),
                () -> assertThat(resultAnnouncerResponseDto.responseDto()
                                                           .numbersFromUser()
                                                           .size()).isEqualTo(6)
                 );

    }
}