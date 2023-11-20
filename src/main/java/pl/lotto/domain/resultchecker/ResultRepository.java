package pl.lotto.domain.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends MongoRepository<Result, String> {

    Optional<Result> findByTicketId(String ticketId);
}