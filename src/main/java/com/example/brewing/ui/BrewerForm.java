package com.example.brewing.ui;

import com.example.brewing.model.Brewer;
import com.example.brewing.model.BrewerType;
import com.example.brewing.model.User;
import com.example.brewing.repositories.BrewerRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Route("brewer-form")
@UIScope
@Theme(variant = Lumo.DARK)
public class BrewerForm extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    BrewerRepository brewerRepository;
    User activeUser;

    public BrewerForm(){
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }

    private TextField brewerNameTF = new TextField();
    private Select<BrewerType> brewerTypeSelect = new Select<>();
    private Button saveButton = new Button("Save");
    private Button backButton = new Button("Go back");
    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        brewerTypeSelect.setLabel("brewer type");
        brewerTypeSelect.setItems(BrewerType.values());
        brewerNameTF.setLabel("brewer name");
        saveButton.addClickListener(buttonClickEvent -> {
            if(!brewerNameTF.isEmpty() && !brewerTypeSelect.isEmpty()) {
                Brewer brewer = new Brewer(brewerTypeSelect.getValue(),brewerNameTF.getValue(), activeUser);
                brewerRepository.save(brewer);
                brewerNameTF.clear();
                brewerTypeSelect.clear();
            } else {
                Notification.show("Fields cannot be empty");

            }
        });
        backButton.addClickListener(click -> UI.getCurrent().navigate("user-page"));
        add(backButton,brewerNameTF,brewerTypeSelect,saveButton);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }
}
