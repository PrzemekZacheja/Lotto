package pl.lotto.infrastucture.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.lotto.infrastucture.loginandregister.dto.TokenRequestDto;
import pl.lotto.infrastucture.loginandregister.dto.TokenResponseDto;

import java.time.*;

@Component
@AllArgsConstructor
public class JwtAuthenticatorFacade {

    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtConfigurationProperties jwtConfigurationProperties;

    public TokenResponseDto authenticate(TokenRequestDto tokenRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                tokenRequestDto.email(),
                tokenRequestDto.password()));
        User principalUser = (User) authentication.getPrincipal();
        String token = generateToken(principalUser);
        String email = principalUser.getUsername();
        return TokenResponseDto.builder()
                               .email(email)
                               .token(token)
                               .build();
    }


    private String generateToken(User principalUser) {
        String secretKey = jwtConfigurationProperties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock)
                                   .toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plus(Duration.ofDays(jwtConfigurationProperties.expirationDays()));
        String issuer = jwtConfigurationProperties.issuer();
        return com.auth0.jwt.JWT
                .create()
                .withSubject(principalUser.getUsername())
                .withExpiresAt(expiresAt)
                .withIssuedAt(now)
                .withIssuer(issuer)
                .sign(algorithm);

    }
}