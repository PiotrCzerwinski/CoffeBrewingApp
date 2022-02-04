package com.example.brewing.ui;

import com.example.brewing.model.Coffee;
import com.example.brewing.model.Grinder;
import com.example.brewing.model.GrinderType;
import com.example.brewing.model.User;
import com.example.brewing.repositories.CoffeeRepository;
import com.example.brewing.repositories.GrinderRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import java.util.stream.IntStream;

@Route("coffee-form")
@UIScope
@Theme(variant = Lumo.DARK)
public class CoffeeForm extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    CoffeeRepository coffeeRepository;
    User activeUser;

    public CoffeeForm(){
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }

    private TextField roasterTF = new TextField("coffee roaster");
    private TextField originTF = new TextField("country of origin");
    private Select<Integer> roastLevelSelect = new Select<>();
    private DatePicker roastDate = new DatePicker("roast date");
    private Button saveButton = new Button("Save");
    private Button backButton = new Button("Go back");

    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        roastLevelSelect.setItems(IntStream.range(1,6).boxed());
        roastLevelSelect.setLabel("roast level");
        saveButton.addClickListener(buttonClickEvent -> {
            if(!roasterTF.isEmpty() && !originTF.isEmpty()
                    && !roastLevelSelect.isEmpty() && !roastDate.isEmpty()) {
                Coffee coffee = new Coffee(roasterTF.getValue(),originTF.getValue(),roastLevelSelect.getValue(),roastDate.getValue(),activeUser);
                coffeeRepository.save(coffee);
                roasterTF.clear();
                originTF.clear();
                roastLevelSelect.clear();
                roastDate.clear();
            } else {
                Notification.show("Fields cannot be empty");

            }
        });
        backButton.addClickListener(click -> UI.getCurrent().navigate("user-page"));
        add(backButton,roasterTF,originTF,roastLevelSelect,roastDate,saveButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }
}
