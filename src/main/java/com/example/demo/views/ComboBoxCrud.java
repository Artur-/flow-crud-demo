package com.example.demo.views;

import com.example.demo.service.Product;
import com.example.demo.service.ProductCrudRepositoryService;
import com.example.demo.util.ComboBoxCrudServiceDataProvider;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "ComboBox Crud Service")
public class ComboBoxCrud extends VerticalLayout {

    public ComboBoxCrud(ProductCrudRepositoryService productService) {
        ComboBox<Product> comboBox = new ComboBox<>("Select a product");
        ComboBoxCrudServiceDataProvider<Product> dataProvider = new ComboBoxCrudServiceDataProvider<>(productService, "name");
        comboBox.setItems(dataProvider);
        comboBox.setItemLabelGenerator(Product::getName);
        add(comboBox);
    }
}
