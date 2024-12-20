package com.example.demo.views;

import com.example.demo.Product;
import com.example.demo.ProductRepository;
import com.example.demo.ProductService;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route("")
public class HomeView extends VerticalLayout {

    public HomeView(ProductService productService, ProductRepository productRepository) {
        Grid<Product> grid = new Grid<>(Product.class, false);
        DataProvider<Product, Void> dataProvider = DataProvider.fromCallbacks(
                query -> productService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), null).stream(),
                query -> (int) productService.count(null));
        grid.setItems(dataProvider);
        grid.addColumns("id", "name", "description", "price", "stockQuantity");
        add(grid);
    }
}
