package com.example.demo.views;

import jakarta.persistence.EntityManager;

import com.example.demo.GenericFilterComponent;
import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import com.example.demo.util.GridCrudServiceDataProvider;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;

@Route
@Menu(title = "Crud Service, generic filter")
public class GridCrudWithFilter extends VerticalLayout {

    private PropertyStringFilter filter;

    public GridCrudWithFilter(ProductCrudRepositoryService productService,
            EntityManager em) {
        GridCrudServiceDataProvider<Product> dataProvider = new GridCrudServiceDataProvider<>(productService);

        GenericFilterComponent filterComponent = new GenericFilterComponent(em, Product.class);
        filterComponent.addFilterChangeListener(e -> {
            this.filter = e.getPropertyFilter();
            
        });
        add(filterComponent);
        add(new Button("Update", e -> {
            dataProvider.setFilter(filter);
        }));

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.setItems(dataProvider);
        grid.addColumns("id", "name", "description", "price", "stockQuantity");
        add(grid);
    }
}
