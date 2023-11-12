package pl.lotto.domain.resultchecker;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ResultRepositoryImpl implements ResultRepository {

    private final Map<String, Result> inMemoryDatabase = new ConcurrentHashMap<>();


    @Override
    public <S extends Result> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Result> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Result> findById(String s) {
        return Optional.ofNullable(inMemoryDatabase.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Result> findAll() {
        return null;
    }

    @Override
    public Iterable<Result> findAllById(Iterable<String> strings) {
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
    public void delete(Result entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Result> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Result> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Result> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Result> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Result> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Result> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Result> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Result> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Result> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Result> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Result> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Result, R> R findBy(Example<S> example,
                                                 Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}