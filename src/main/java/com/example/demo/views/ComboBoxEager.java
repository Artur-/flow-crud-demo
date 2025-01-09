package com.example.demo.views;

import com.example.demo.service.CustomService;
import com.example.demo.service.Product;
import com.example.demo.util.ComboBoxServiceDataProvider;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route
@Menu(title = "ComboBox eager service method")
public class ComboBoxEager extends VerticalLayout {

    public ComboBoxEager(CustomService customService) {
        ComboBox<Product> comboBox = new ComboBox<>("Select a product");
        comboBox.setItems(customService.findAllEager());
        comboBox.setItemLabelGenerator(Product::getName);
        add(comboBox);
    }
}
