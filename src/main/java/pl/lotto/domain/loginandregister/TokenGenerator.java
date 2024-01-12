package pl.lotto.domain.loginandregister;

import java.security.SecureRandom;
import java.util.Arrays;

class TokenGenerator {

    static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return Arrays.toString(bytes);
    }
}