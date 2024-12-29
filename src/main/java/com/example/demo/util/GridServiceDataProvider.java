package com.example.demo.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

public class GridServiceDataProvider<T> extends AbstractBackEndDataProvider<T, Void> {

    private final Function<Query<T, Void>, Stream<T>> queryFunction;
    private final Function<Query<T, Void>, Long> countFunction;

    public GridServiceDataProvider(Supplier<List<T>> query) {
        this.queryFunction = q -> query.get().subList(q.getOffset(), q.getOffset() + q.getLimit()).stream();
        this.countFunction = q -> (long) query.get().size();
    }

    public GridServiceDataProvider(Function<Pageable, List<T>> query) {
        this.queryFunction = q -> query.apply(VaadinSpringDataHelpers.toSpringPageRequest(q)).stream();
        this.countFunction = q -> -1L; // FIXME
    }

    public GridServiceDataProvider(Function<Pageable, List<T>> query, Supplier<Long> countQuery) {
        this.queryFunction = q -> query.apply(VaadinSpringDataHelpers.toSpringPageRequest(q)).stream();
        this.countFunction = q -> countQuery.get();
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, Void> query) {
        return queryFunction.apply(query);
    }

    @Override
    protected int sizeInBackEnd(Query<T, Void> query) {
        return countFunction.apply(query).intValue();
    }

}
