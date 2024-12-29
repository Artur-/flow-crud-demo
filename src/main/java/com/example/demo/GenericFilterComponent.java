package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

import java.util.List;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter.Matcher;

public class GenericFilterComponent extends VerticalLayout {

    private Select<String> filterPropertySelect = new Select<>();
    private Select<Matcher> matcherSelect = new Select<>();
    private PropertyStringFilter filter;
    private TextField filterField;

    public GenericFilterComponent(EntityManager em, Class<?> type) {
        EntityType<?> meta = em.getMetamodel().entity(type);
        List<String> properties = meta.getAttributes().stream().filter(attr -> !attr.getName().equals("id"))
                .map(attr -> attr.getName()).toList();
        filterPropertySelect.setItems(properties);
        filterPropertySelect.setValue(properties.get(0));

        matcherSelect.setItems(Matcher.values());
        matcherSelect.setValue(Matcher.CONTAINS);
        filterField = new TextField();
        filterField.addValueChangeListener(this::updateFilter);
        matcherSelect.addValueChangeListener(this::updateFilter);
        filterPropertySelect.addValueChangeListener(this::updateFilter);

        add(filterPropertySelect);
        add(matcherSelect);
        add(filterField);
    }

    private void updateFilter(ComponentEvent<?> event) {
        PropertyStringFilter propertyFilter = new PropertyStringFilter();
        propertyFilter.setPropertyId(filterPropertySelect.getValue());
        propertyFilter.setFilterValue(filterField.getValue());
        propertyFilter.setMatcher(matcherSelect.getValue());

        this.filter = propertyFilter;
        this.getEventBus().fireEvent(new FilterChangeEvent(this, filter));
    }

    public PropertyStringFilter getFilter() {
        return filter;
    }

    public Registration addFilterChangeListener(ComponentEventListener<FilterChangeEvent> listener) {
        return this.getEventBus().addListener(FilterChangeEvent.class, listener);
    }

    public static class FilterChangeEvent extends ComponentEvent<GenericFilterComponent> {
        private PropertyStringFilter propertyFilter;

        public FilterChangeEvent(GenericFilterComponent source, PropertyStringFilter propertyFilter) {
            super(source, false);
            this.propertyFilter = propertyFilter;
        }

        public PropertyStringFilter getPropertyFilter() {
            return propertyFilter;
        }
    }
}
