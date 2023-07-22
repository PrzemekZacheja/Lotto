package pl.lotto.domain.numbersreceiver;

import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.*;
import pl.lotto.domain.drawdategenerator.*;

import java.time.*;
import java.util.*;
import java.util.function.*;

@Configuration
public class NumberReceiverFacadeConfiguration {

    @Bean
    NumberValidator numberValidator() {
        return new NumberValidator();
    }

    @Bean
    NumberReceiverRepository numberReceiverRepository() {
        return new NumberReceiverRepository() {

            @Override
            public <S extends Ticket> List<S> saveAll(Iterable<S> entities) {
                return Collections.emptyList();
            }

            @Override
            public Optional<Ticket> findById(String s) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(String s) {
                return false;
            }

            @Override
            public List<Ticket> findAll() {
                return null;
            }

            @Override
            public Iterable<Ticket> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(String s) {

            }

            @Override
            public void delete(Ticket entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends String> strings) {

            }

            @Override
            public void deleteAll(Iterable<? extends Ticket> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public List<Ticket> findAll(Sort sort) {
                return Collections.emptyList();
            }

            @Override
            public Page<Ticket> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Ticket> S insert(S entity) {
                return null;
            }

            @Override
            public <S extends Ticket> List<S> insert(Iterable<S> entities) {
                return Collections.emptyList();
            }

            @Override
            public <S extends Ticket> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Ticket> List<S> findAll(Example<S> example) {
                return Collections.emptyList();
            }

            @Override
            public <S extends Ticket> List<S> findAll(Example<S> example, Sort sort) {
                return Collections.emptyList();
            }

            @Override
            public <S extends Ticket> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Ticket> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Ticket> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Ticket, R> R findBy(
                    Example<S> example,
                    Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction
                                                 ) {
                return null;
            }

            @Override
            public Ticket save(Ticket ticket) {
                return null;
            }

            @Override
            public List<Ticket> findAllTicketsByDrawDate(LocalDateTime dateOfDraw) {
                return Collections.emptyList();
            }
        };
    }

    @Bean
    HashGenerable hashGenerable() {
        return new HashGenerator();
    }

    @Bean
    public NumberReceiverFacade numberReceiverFacade(
            NumberValidator numberValidator,
            NumberReceiverRepository numberRepository,
            HashGenerable hashGenerable,
            DrawDateFacade drawDateFacade
                                                    ) {
        return new NumberReceiverFacade(numberValidator, numberRepository, hashGenerable, drawDateFacade);
    }
}