package com.example.demo.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.CountService;
import com.vaadin.flow.spring.data.ListService;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.spring.data.filter.AndFilter;
import com.vaadin.flow.spring.data.filter.Filter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;

public class ComboBoxCrudServiceDataProvider<T> extends AbstractBackEndDataProvider<T, String> {

    private final ListService<T> service;
    private Filter filter;
    private Function<String, Filter> filterConverter;

    public ComboBoxCrudServiceDataProvider(ListService<T> service, String comboboxFilterProperty) {
        this(service, filterString -> {
            PropertyStringFilter filter = new PropertyStringFilter();
            filter.setPropertyId(comboboxFilterProperty);
            filter.setFilterValue(filterString);
            filter.setMatcher(PropertyStringFilter.Matcher.CONTAINS);
            return filter;
        });
    }

    public ComboBoxCrudServiceDataProvider(ListService<T> service, Function<String, Filter> filterConverter) {
        this.service = service;
        this.filterConverter = filterConverter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
        this.refreshAll();
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, String> query) {
        return service.list(VaadinSpringDataHelpers.toSpringPageRequest(query), getActiveFilter(query)).stream();
    }

    private Filter getActiveFilter(Query<T, String> query) {
        if (query.getFilter().isEmpty()) {
            return filter;
        }
        String comboboxFilterString = query.getFilter().get();

        Filter comboboxFilter = filterConverter.apply(comboboxFilterString);
        if (filter == null) {
            return comboboxFilter;
        }
        AndFilter andFilter = new AndFilter();
        andFilter.setChildren(List.of(filter, comboboxFilter));
        return andFilter;
    }

    @Override
    protected int sizeInBackEnd(Query<T, String> query) {
        if (service instanceof CountService countService) {
            return (int) countService.count(getActiveFilter(query));
        }

        return -1; // FIXME
    }

}
