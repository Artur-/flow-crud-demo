package com.example.demo.views;

import com.example.demo.service.CustomService;
import com.example.demo.service.Product;
import com.example.demo.util.GridServiceDataProvider;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "Custom service, lazy, count method")
public class GridLazyWithCount extends VerticalLayout {

    public GridLazyWithCount(CustomService customService) {

        GridServiceDataProvider<Product> dataProvider = new GridServiceDataProvider<>(
                pageable -> customService.findProductsLazy(pageable), () -> customService.count());

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItems(dataProvider);
        add(grid);
    }

}
