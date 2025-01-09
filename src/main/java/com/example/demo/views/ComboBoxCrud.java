package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import org.jspecify.annotations.Nullable;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.filter.Filter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;

@Route
@Menu(title = "ComboBox Crud Service")
public class ComboBoxCrud extends VerticalLayout {

    public ComboBoxCrud(ProductCrudRepositoryService productService) {
        ComboBox<Product> comboBox = new ComboBox<>("Select a product");

        comboBox.setItemsPageable((pageable, filter) -> productService.list(pageable, createFilter(filter)),
                (pageable, filter) -> productService.count(createFilter(filter)));
        comboBox.setItemLabelGenerator(Product::getName);
        add(comboBox);
    }

    private @Nullable Filter createFilter(String comboboxFilterString) {
        PropertyStringFilter filter = new PropertyStringFilter();
        filter.setPropertyId("name");
        filter.setFilterValue(comboboxFilterString);
        filter.setMatcher(PropertyStringFilter.Matcher.CONTAINS);
        return filter;

    }
}
