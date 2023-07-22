package pl.lotto;

import com.fasterxml.jackson.databind.*;
import com.github.tomakehurst.wiremock.junit5.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;
import org.testcontainers.containers.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.utility.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;

@SpringBootTest(classes = {LottoSpringBootApplication.class, IntegrationConfiguration.class})
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {

    public static final String WIRE_MOCK_HOST = "http://localhost";
    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
                                                                      .options(wireMockConfig().dynamicPort())
                                                                      .build();
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("lotto.number_generator.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("lotto.number_generator.http.client.config.port", () -> wireMockServer.getPort());
    }

}