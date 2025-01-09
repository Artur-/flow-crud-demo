package com.example.demo.util;

import java.util.stream.Stream;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.CountService;
import com.vaadin.flow.spring.data.ListService;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.spring.data.filter.Filter;

public class GridListServiceDataProvider<T> extends AbstractBackEndDataProvider<T, Void> {

    private final ListService<T> service;
    private Filter filter;

    public GridListServiceDataProvider(ListService<T> service) {
        this.service = service;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
        this.refreshAll();
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, Void> query) {
        return service.list(VaadinSpringDataHelpers.toSpringPageRequest(query), filter).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<T, Void> query) {
        if (service instanceof CountService countService) {
            return (int) countService.count(filter);
        }

        return -1; // FIXME
    }

}
