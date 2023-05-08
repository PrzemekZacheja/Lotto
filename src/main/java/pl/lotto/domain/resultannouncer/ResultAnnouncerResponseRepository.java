package pl.lotto.domain.resultannouncer;

public interface ResultAnnouncerResponseRepository {

    ResultAnnouncerResponse save(ResultAnnouncerResponse resultResponse);

    ResultAnnouncerResponse findResponseById(String id);
}