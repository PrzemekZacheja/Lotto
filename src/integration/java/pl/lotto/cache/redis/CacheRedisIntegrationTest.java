package pl.lotto.cache.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.infrastucture.loginandregister.dto.TokenResponseDto;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("security-integration")
class CacheRedisIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @SpyBean
    ResultAnnouncerFacade resultAnnouncerFacade;

//    @Autowired
//    CacheManager cacheManager;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.host",
                     () -> REDIS.getFirstMappedPort()
                                .toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PTS1S");
    }

    @Test
    void should_cache_data_in_redis() throws Exception {

        //1. User made POST /register request with email=someUser and password=somePassword
        //given & when
        ResultActions registerRequest = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                         .content("""
                                                                                  {
                                                                                      "email": "someUser",
                                                                                      "password": "somePassword"
                                                                                  }
                                                                                  """.trim()));
        //then
        registerRequest.andExpect(status().isCreated())
                       .andReturn();

        //2. User made POST /token with email=someUser and password=somePassword and system returned JWT Token
        //given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/token").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                          .content("""
                                                                                   {
                                                                                       "email": "someUser",
                                                                                       "password": "somePassword"
                                                                                   }
                                                                                   """.trim()));
        //then
        ResultActions successResultToken = successLoginRequest.andExpect(status().isOk());
        String contentAsStringToken = successResultToken.andReturn()
                                                        .getResponse()
                                                        .getContentAsString();
        TokenResponseDto tokenResponseDto = objectMapper.readValue(contentAsStringToken, TokenResponseDto.class);
        String token = tokenResponseDto.token();


        // 3. user made POST /inputNumbers with numbers=1,2,3,4,5,6 and system returned ticketId
        //given & when
        ResultActions inputNumbersRequest =
                mockMvc.perform(post("/inputNumbers").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                     .content("""
                                                              {
                                                                  "inputNumbers": [1,2,3,4,5,6]
                                                                  }
                                                                  """.trim())
                                                     .header("Authorization",
                                                             "Bearer " + token));
        //then
        String contentAsString = inputNumbersRequest.andReturn()
                                                    .getResponse()
                                                    .getContentAsString();
        TicketDto ticketDto = objectMapper.readValue(contentAsString, TicketDto.class);
        String ticketId = ticketDto.ticketId();


        //3. should save to cache response from ResultAnnouncer
        //given & when
        mockMvc.perform(get("/results/" + ticketId).header(
                "Authorization",
                "Bearer " + token));
        //then
        verify(resultAnnouncerFacade, times(1)).generateResponseByIdTicket((ticketId));
//        assertThat(cacheManager.getCacheNames()
//                               .contains("resultAnnouncerResponse")).isTrue();

        //4.  should return cached response from Redis
        //given & when & then
        await()
                .atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                                   mockMvc.perform(get("/results/" + ticketId).header(
                                           "Authorization",
                                           "Bearer " + token));
                                   verify(resultAnnouncerFacade, atLeast(2))
                                           .generateResponseByIdTicket((ticketId));
                               }
                              );

    }

}