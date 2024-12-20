package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.spring.data.filter.Filter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter.Matcher;

@Route("filter")
public class GridWithFilter extends VerticalLayout {

    private Filter filter = null;
    private Select<String> filterPropertySelect = new Select<>();
    private Select<Matcher> matcherSelect = new Select<>();
    private Grid<Product> grid;
    private String filterText = "";

    public GridWithFilter(ProductService productService, ProductRepository productRepository, EntityManager em) {
        DataProvider<Product, Void> dataProvider = DataProvider.fromCallbacks(
                query -> productService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), filter).stream(),
                query -> (int) productService.count(filter));

        EntityType<Product> meta = em.getMetamodel().entity(Product.class);
        List<String> properties = meta.getAttributes().stream().filter(attr -> !attr.getName().equals("id"))
                .map(attr -> attr.getName()).toList();
        filterPropertySelect.setItems(properties);
        filterPropertySelect.setValue(properties.get(0));

        matcherSelect.setItems(Matcher.values());
        matcherSelect.setValue(Matcher.CONTAINS);
        TextField filterField = new TextField("Filter by name");
        filterField.addValueChangeListener(e -> {
            this.filterText = e.getValue();
        });

        add(filterPropertySelect);
        add(matcherSelect);
        add(filterField);
        add(new Button("Update", e -> {
            PropertyStringFilter propertyFilter = new PropertyStringFilter();
            propertyFilter.setPropertyId(filterPropertySelect.getValue());
            propertyFilter.setFilterValue(filterText);
            propertyFilter.setMatcher(matcherSelect.getValue());
            filter = propertyFilter;

            dataProvider.refreshAll();
        }));

        grid = new Grid<>(Product.class, false);
        grid.setItems(dataProvider);
        grid.addColumns("id", "name", "description", "price", "stockQuantity");
        add(grid);
    }
}
