package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import com.example.demo.util.GridCrudServiceDataProvider;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "Crud Service")
public class GridCrud extends VerticalLayout {

    public GridCrud(ProductCrudRepositoryService productService) {
        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.setItems(new GridCrudServiceDataProvider<>(productService));
        grid.addColumns("id", "name", "description", "price", "stockQuantity");
        add(grid);
    }
}
