package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.jpa.CrudRepositoryService;
import com.vaadin.hilla.BrowserCallable;

@Service
@BrowserCallable
@AnonymousAllowed
public class ProductCrudRepositoryService extends CrudRepositoryService<Product, Long, ProductRepository> {
    public ProductCrudRepositoryService(ProductRepository repository) {
        super(repository);
    }

}
