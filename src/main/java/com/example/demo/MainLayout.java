package com.example.demo;

import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.menu.MenuConfiguration;

@Layout
public class MainLayout extends Div implements RouterLayout {

    private HorizontalLayout layout;

    public MainLayout() {
        layout = new HorizontalLayout();
H1 demos = new H1("Demos");
        SideNav sideNav = new SideNav();
        VerticalLayout verticallayout = new VerticalLayout();
        verticallayout.setWidth("-1");
verticallayout.add(demos);
        verticallayout.add(sideNav);
        layout.add(verticallayout);
        MenuConfiguration.getMenuEntries().stream().map(menu -> new SideNavItem(menu.title(), menu.path()))
                .forEach(sideNav::addItem);
        add(layout);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (layout.getComponentCount() > 1) {
            layout.remove(layout.getChildren().toList().get(1));
        }
        if (content != null) {
            layout.add((Component) content);
        }
    }
}
