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
import com.vaadin.flow.spring.data.jpa.PropertyStringFilterSpecification;

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
                Product.class, PropertyStringFilterSpecification::new);
        return repository.findAll(spec, pageable).toList();
    }

    public List<Product> findAllEager() {
        return repository.findAll();
    }

    public List<Product> findAllEager(Filter filter) {
        return repository
                .findAll(JpaFilterConverter.toSpec(filter, Product.class, PropertyStringFilterSpecification::new));
    }

    public List<Product> findAllEager(String nameOrDescription) {
        return repository
                .findAll(JpaFilterConverter.toSpec(createNameOrDescriptionFilter(nameOrDescription), Product.class,
                        PropertyStringFilterSpecification::new));
    }

    public long count() {
        return repository.count();
    }

    public long count(String nameOrDescription) {
        return count(createNameOrDescriptionFilter(nameOrDescription));
    }

    public long count(Filter filter) {
        return repository
                .count(JpaFilterConverter.toSpec(filter, Product.class, PropertyStringFilterSpecification::new));
    }

    private Filter createNameOrDescriptionFilter(String nameOrDescription) {
        PropertyStringFilter nameFilter = new PropertyStringFilter("name", Matcher.CONTAINS, nameOrDescription);

        PropertyStringFilter descriptionFilter = new PropertyStringFilter("description", Matcher.CONTAINS,
                nameOrDescription);

        return new OrFilter(nameFilter, descriptionFilter);
    }

}
