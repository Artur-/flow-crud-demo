package com.example.demo;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route("")
@Menu(title = "Basic grid")
public class BasicGrid extends VerticalLayout {

    public BasicGrid(ProductService productService, ProductRepository productRepository) {
        Grid<Product> grid = new Grid<>(Product.class, false);
        DataProvider<Product, Void> dataProvider = DataProvider.fromCallbacks(
                query -> productService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), null).stream(),
                query -> (int) productService.count(null));
        grid.setItems(dataProvider);
        grid.addColumns("id", "name", "description", "price", "stockQuantity");
        add(grid);
    }
}
