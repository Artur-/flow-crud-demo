package com.example.demo.views;

import com.example.demo.service.CustomService;
import com.example.demo.service.Product;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "Custom service, lazy, count method, filter")
public class GridLazyWithCountWithFilter extends VerticalLayout {

    private String nameOrDescription = "";
    private Grid<Product> grid;

    public GridLazyWithCountWithFilter(CustomService customService) {

        TextField filterField = new TextField("Name or description");
        filterField.addValueChangeListener(e -> {
            nameOrDescription = e.getValue();
        });
        add(filterField);
        add(new Button("Update", e -> {
            grid.getDataProvider().refreshAll();
        }));

        grid = new Grid<>(Product.class, false);
        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItemsPageable(pageable -> customService.findProductsLazy(pageable, nameOrDescription),
                pageable -> customService.count(nameOrDescription));
        add(grid);
    }

}
