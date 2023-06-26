package pl.lotto.domain.numbersreceiver;

import java.util.UUID;

class HashGenerator implements HashGenerable {

    @Override
    public String getHash() {
        return UUID.randomUUID()
                .toString();
    }
}