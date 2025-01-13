package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "List Service")
public class GridList extends VerticalLayout {

    public GridList(ProductCrudRepositoryService productService) {
        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.setItemsPageable(pageable -> productService.list(pageable, null));
        grid.addColumns("id", "name", "description", "price", "stockQuantity");
        add(grid);
    }
}
