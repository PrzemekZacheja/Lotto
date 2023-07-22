package pl.lotto.domain.numbersgenerator;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Repository
public interface NumbersGeneratorRepository extends MongoRepository<WinnerNumbers, String> {


    Optional<WinnerNumbers> findWinningNumberByDrawDate(LocalDateTime localDateTime);
}