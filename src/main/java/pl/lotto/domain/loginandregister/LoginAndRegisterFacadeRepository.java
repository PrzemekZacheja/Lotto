package pl.lotto.domain.loginandregister;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginAndRegisterFacadeRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}