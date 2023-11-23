package pl.lotto.infrastucture.numbersgenerator.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
public class WinningNumberGeneratorRestTemplate implements WinningNumberGenerable {

    public static final int WINNING_NUMBERS = 6;
    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;


    @Override
    public Set<Integer> generateWinningRandomNumbers(final int lowerBand, final int upperBand, final int count) {
        String urlForService = getUrlForService();
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                                               .queryParam("min", lowerBand)
                                               .queryParam("max", upperBand)
                                               .queryParam("count", count)
                                               .toUriString();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<Integer>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
            });
            return Objects.requireNonNull(responseEntity.getBody())
                          .stream()
                          .limit(WINNING_NUMBERS)
                          .collect(Collectors.toSet());

        } catch (ResourceAccessException e) {
            log.error("Error while generateWinningRandomNumbers via http client " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUrlForService() {
        return uri + ":" + port + "/api/v1.0/random";
    }
}