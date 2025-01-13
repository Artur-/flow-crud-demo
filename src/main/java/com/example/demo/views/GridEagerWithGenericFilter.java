package com.example.demo.views;

import jakarta.persistence.EntityManager;

import com.example.demo.GenericFilterComponent;
import com.example.demo.service.CustomService;
import com.example.demo.service.Product;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;

@Route
@Menu(title = "Grid with eager loading and generic filtering")
public class GridEagerWithGenericFilter extends VerticalLayout {

    private PropertyStringFilter filter;
    private Grid<Product> grid = new Grid<>(Product.class, false);

    public GridEagerWithGenericFilter(CustomService customService, EntityManager em) {

        GenericFilterComponent filterComponent = new GenericFilterComponent(em, Product.class);
        filterComponent.addFilterChangeListener(e -> {
            filter = e.getPropertyFilter();
        });
        add(filterComponent);
        add(new Button("Update", e -> {
            grid.setItems(customService.findAllEager(filter));
        }));

        grid.addColumns("name", "description", "price", "stockQuantity");
        grid.setItems(customService.findAllEager(filter));
        add(grid);
    }
}
