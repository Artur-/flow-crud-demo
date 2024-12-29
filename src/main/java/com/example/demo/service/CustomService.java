package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.data.filter.Filter;
import com.vaadin.flow.spring.data.filter.OrFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter.Matcher;
import com.vaadin.flow.spring.data.jpa.JpaFilterConverter;

@Service
public class CustomService {

    private final ProductRepository repository;

    CustomService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findProductsLazy(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }

    public List<Product> findProductsLazy(Pageable pageable, String nameOrDescription) {
        Specification<Product> spec = JpaFilterConverter.toSpec(createNameOrDescriptionFilter(nameOrDescription),
                Product.class);
        return repository.findAll(spec, pageable).toList();
    }

    public List<Product> findAllEager() {
        return repository.findAll();
    }

    public List<Product> findAllEager(Filter filter) {
        return repository.findAll(JpaFilterConverter.toSpec(filter, Product.class));
    }

    public List<Product> findAllEager(String nameOrDescription) {
        return repository
                .findAll(JpaFilterConverter.toSpec(createNameOrDescriptionFilter(nameOrDescription), Product.class));
    }

    public long count() {
        return repository.count();
    }

    public long count(Filter filter) {
        return repository.count(JpaFilterConverter.toSpec(filter, Product.class));
    }

    private Filter createNameOrDescriptionFilter(String nameOrDescription) {
        PropertyStringFilter nameFilter = new PropertyStringFilter();
        nameFilter.setPropertyId("name");
        nameFilter.setMatcher(Matcher.CONTAINS);
        nameFilter.setFilterValue(nameOrDescription);

        PropertyStringFilter descriptionFilter = new PropertyStringFilter();
        descriptionFilter.setPropertyId("description");
        descriptionFilter.setMatcher(Matcher.CONTAINS);
        descriptionFilter.setFilterValue(nameOrDescription);

        OrFilter filter = new OrFilter();
        filter.setChildren(List.of(nameFilter, descriptionFilter));

        return filter;
    }

}
