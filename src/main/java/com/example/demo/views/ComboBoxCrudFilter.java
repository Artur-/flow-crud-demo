package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import com.example.demo.util.ComboBoxCrudServiceDataProvider;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter.Matcher;

@Route
@Menu(title = "ComboBox Crud Service using filter")
public class ComboBoxCrudFilter extends VerticalLayout {

    public ComboBoxCrudFilter(ProductCrudRepositoryService productService) {
        ComboBox<Product> comboBox = new ComboBox<>("Select a product");
        ComboBoxCrudServiceDataProvider<Product> dataProvider = new ComboBoxCrudServiceDataProvider<>(productService,
                "name");

        PropertyStringFilter containsAc = new PropertyStringFilter();
        containsAc.setPropertyId("name");
        containsAc.setFilterValue("ac");
        containsAc.setMatcher(Matcher.CONTAINS);
        dataProvider.setFilter(containsAc);
 
        comboBox.setItems(dataProvider);
        comboBox.setItemLabelGenerator(Product::getName);
        add(comboBox);
    }
}
