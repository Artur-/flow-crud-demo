package com.example.demo;

import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.data.jpa.CrudRepositoryService;

@Service
public class ProductService extends CrudRepositoryService<Product, Long, ProductRepository> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }

}
