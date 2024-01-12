package pl.lotto.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@AllArgsConstructor
class NumbersGeneratorRepositoryForTest implements NumbersGeneratorRepository {

    private final Map<LocalDateTime, WinnerNumbers> localDateTimeWinnerNumbersMap = new ConcurrentHashMap<>();

    @Override
    public <S extends WinnerNumbers> S save(final S entity) {
        localDateTimeWinnerNumbersMap.put(entity.drawDate(), entity);
        return entity;
    }

    @Override
    public Optional<WinnerNumbers> findById(final String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(final String s) {
        return false;
    }

    @Override
    public Iterable<WinnerNumbers> findAllById(final Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(final String s) {

    }

    @Override
    public void delete(final WinnerNumbers entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(final Iterable<? extends WinnerNumbers> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsByDrawDate(LocalDateTime drawDate) {
        return localDateTimeWinnerNumbersMap.containsKey(drawDate);
    }

    @Override
    public Optional<WinnerNumbers> findByDrawDate(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTimeWinnerNumbersMap.get(localDateTime));
    }

    @Override
    public <S extends WinnerNumbers> List<S> saveAll(final Iterable<S> entities) {
        return null;
    }

    @Override
    public List<WinnerNumbers> findAll() {
        return null;
    }

    @Override
    public List<WinnerNumbers> findAll(final Sort sort) {
        return null;
    }

    @Override
    public <S extends WinnerNumbers> S insert(final S entity) {
        return null;
    }

    @Override
    public <S extends WinnerNumbers> List<S> insert(final Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends WinnerNumbers> List<S> findAll(final Example<S> example) {
        return null;
    }

    @Override
    public <S extends WinnerNumbers> List<S> findAll(final Example<S> example, final Sort sort) {
        return null;
    }

    @Override
    public Page<WinnerNumbers> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WinnerNumbers> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WinnerNumbers> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WinnerNumbers> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WinnerNumbers> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends WinnerNumbers, R> R findBy(
            final Example<S> example,
            final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction
                                                ) {
        return null;
    }
}