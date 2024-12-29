package com.example.demo.views;

import jakarta.persistence.EntityManager;

import com.example.demo.service.CustomService;
import com.example.demo.service.Product;
import com.example.demo.util.GridServiceDataProvider;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "Grid with eager loading and custom filtering")
public class GridEagerWithCustomFilter extends VerticalLayout {

    private String nameOrDescription = null;

    public GridEagerWithCustomFilter(CustomService customService, EntityManager em) {

        GridServiceDataProvider<Product> dataProvider = new GridServiceDataProvider<>(
                () -> customService.findAllEager(nameOrDescription));

        TextField filterField = new TextField("Name or description");
        filterField.addValueChangeListener(e -> {
            nameOrDescription = e.getValue();
        });
        add(filterField);
        add(new Button("Update", e -> {
            dataProvider.refreshAll();
        }));

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItems(dataProvider);
        add(grid);
    }
}
