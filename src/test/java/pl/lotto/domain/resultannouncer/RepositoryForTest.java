package pl.lotto.domain.resultannouncer;

import java.util.*;
import java.util.concurrent.*;

class RepositoryForTest implements ResultAnnouncerResponseRepository {
    Map<String, ResultAnnouncerResponse> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public ResultAnnouncerResponse save(ResultAnnouncerResponse resultResponse) {
        return databaseInMemory.put(resultResponse.ticketId(), resultResponse);
    }

    @Override
    public ResultAnnouncerResponse findResponseById(String id) {
        return databaseInMemory.get(id);
    }
}