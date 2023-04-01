package pl.lotto.domain.numbers_receiver;

import java.util.UUID;

class HashGenerator implements HashGenerable {

    @Override
    public String getHash() {
        return UUID.randomUUID().toString();
    }
}