package pl.lotto.domain.result_announcer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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