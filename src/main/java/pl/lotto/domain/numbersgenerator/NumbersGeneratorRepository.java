package pl.lotto.domain.numbersgenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NumbersGeneratorRepository extends MongoRepository<WinnerNumbers, String> {


    Optional<WinnerNumbers> findByDrawDate(LocalDateTime localDateTime);
}