package com.example.demo.views;

import com.example.demo.service.CustomService;
import com.example.demo.service.Product;
import com.example.demo.util.GridServiceDataProvider;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "Grid with eager loading")
public class GridEager extends VerticalLayout {

    public GridEager(CustomService customService) {

        GridServiceDataProvider<Product> dataProvider = new GridServiceDataProvider<>(
                () -> customService.findAllEager());
        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItems(dataProvider);
        add(grid);
    }
}
