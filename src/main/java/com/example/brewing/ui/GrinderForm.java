package com.example.brewing.ui;

import com.example.brewing.model.*;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.GrinderRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
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

@Route("grinder-form")
@UIScope
@Theme(variant = Lumo.DARK)
public class GrinderForm extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    GrinderRepository grinderRepository;
    User activeUser;

    public GrinderForm(){
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }

    private TextField manufacturerTF = new TextField();
    private TextField modelTF = new TextField();
    private Select<GrinderType> grinderTypeSelect = new Select<>();
    private Button saveButton = new Button("Save");
    private Button backButton = new Button("Go back");
    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        manufacturerTF.setLabel("manufacturer");
        modelTF.setLabel("grinder model");
        grinderTypeSelect.setLabel("grinder type");
        grinderTypeSelect.setItems(GrinderType.values());

        saveButton.addClickListener(buttonClickEvent -> {
            if(!manufacturerTF.isEmpty() && !modelTF.isEmpty() && !grinderTypeSelect.isEmpty()) {
                Grinder grinder = new Grinder(manufacturerTF.getValue(),modelTF.getValue(),grinderTypeSelect.getValue(),activeUser);
                grinderRepository.save(grinder);
                manufacturerTF.clear();
                modelTF.clear();
                grinderTypeSelect.clear();
            } else {
                Notification.show("Fields cannot be empty");

            }
        });
        backButton.addClickListener(click -> UI.getCurrent().navigate("user-page"));
        add(backButton,manufacturerTF,modelTF,grinderTypeSelect,saveButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }
}