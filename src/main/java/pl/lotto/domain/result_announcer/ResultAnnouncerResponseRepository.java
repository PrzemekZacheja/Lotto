package pl.lotto.domain.result_announcer;

public interface ResultAnnouncerResponseRepository {

    ResultAnnouncerResponse save(ResultAnnouncerResponse resultResponse);

    ResultAnnouncerResponse findResponseById(String id);
}