package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import com.example.demo.util.GridServiceDataProvider;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route
@Menu(title = "Basic grid")
public class GridFromCallbacks extends VerticalLayout {

    public GridFromCallbacks(ProductCrudRepositoryService productService) {

        DataProvider<Product, Void> dataProvider = DataProvider.fromCallbacks(
                query -> productService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), null).stream(),
                query -> (int) productService.count(null));

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItems(dataProvider);
        add(grid);
    }
}
