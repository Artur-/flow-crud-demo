package com.example.demo;

import org.springframework.stereotype.Service;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.jpa.CrudRepositoryService;
import com.vaadin.hilla.BrowserCallable;

@Service
@BrowserCallable
@AnonymousAllowed
public class ProductService extends CrudRepositoryService<Product, Long, ProductRepository> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }

}
