package com.example.demo.views;

import com.example.demo.service.CustomService;
import com.example.demo.service.Product;
import com.example.demo.util.GridServiceDataProvider;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "Custom service, lazy, no count method")
public class GridLazy extends VerticalLayout {

    public GridLazy(CustomService customService) {

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItemsPageable(pageable -> customService.findProductsLazy(pageable));
        add(grid);
    }

}
