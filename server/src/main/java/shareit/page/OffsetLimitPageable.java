package shareit.page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

public class OffsetLimitPageable implements Pageable {

    private final int offset;
    private final int limit;
    private final Sort sort;

    protected OffsetLimitPageable(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public static Pageable of(Integer from, Integer size) {
        if (from == null && size == null) {
            from = 0;
            size = DEFAULT_PAGE_SIZE;
        }
        validateOrThrowException(from, size);
        return new OffsetLimitPageable(saveUnboxing(from), saveUnboxing(size), Sort.unsorted());
    }

    public static Pageable of(Integer from, Integer size, Sort sort) {
        if (from == null && size == null) {
            from = 0;
            size = DEFAULT_PAGE_SIZE;
        }
        validateOrThrowException(from, size);
        return new OffsetLimitPageable(saveUnboxing(from), saveUnboxing(size), sort);
    }

    private static void validateOrThrowException(Integer from, Integer size) {
        if (saveUnboxing(size) < 1 || saveUnboxing(from) < 0) {
            throw new ValidationException("from must be positive and size must be more then 0");
        }
    }

    public static int saveUnboxing(Integer value) {
        return Optional.ofNullable(value).orElse(0);
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }
}

