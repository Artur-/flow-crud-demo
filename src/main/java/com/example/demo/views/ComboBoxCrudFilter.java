package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import org.jspecify.annotations.Nullable;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.filter.AndFilter;
import com.vaadin.flow.spring.data.filter.Filter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter.Matcher;

@Route
@Menu(title = "ComboBox Crud Service using custom filter")
public class ComboBoxCrudFilter extends VerticalLayout {

    public ComboBoxCrudFilter(ProductCrudRepositoryService productService) {

        add(new H5("Only shows products with 'ac' in the name"));
        ComboBox<Product> comboBox = new ComboBox<>("Select a product");

        comboBox.setItemsPageable((pageable, filter) -> productService.list(pageable, createFilter(filter)),
                (pageable, filter) -> productService.count(createFilter(filter)));
        comboBox.setItemLabelGenerator(Product::getName);
        add(comboBox);
    }

    private @Nullable Filter createFilter(String comboboxFilterString) {
        PropertyStringFilter containsAc = new PropertyStringFilter("name", Matcher.CONTAINS, "ac");

        PropertyStringFilter filter = new PropertyStringFilter("name", Matcher.CONTAINS, comboboxFilterString);

        return new AndFilter(containsAc, filter);
    }
}
