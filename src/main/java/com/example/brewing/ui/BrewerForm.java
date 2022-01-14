package com.example.brewing.ui;

import com.example.brewing.model.Brewer;
import com.example.brewing.model.BrewerType;
import com.example.brewing.repositories.BrewerRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Route("brewer-form")
@UIScope
@Theme(variant = Lumo.DARK)
public class BrewerForm extends VerticalLayout {
    @Autowired
    BrewerRepository brewerRepository;

    private TextField brewerNameTF = new TextField();
    private Select<BrewerType> brewerTypeSelect = new Select<>();
    private Button saveButton = new Button("Save brewer");
    private Button backButton = new Button("Go back");
    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        brewerTypeSelect.setLabel("brewer type");
        brewerTypeSelect.setItems(BrewerType.values());
        brewerNameTF.setLabel("brewer name");
        saveButton.addClickListener(buttonClickEvent -> {
            if(!brewerNameTF.isEmpty() && !brewerTypeSelect.isEmpty()) {
                Brewer brewer = new Brewer(brewerTypeSelect.getValue(),new ArrayList<>(),brewerNameTF.getValue());
                brewerRepository.save(brewer);
                brewerNameTF.clear();
                brewerTypeSelect.clear();
            } else {
                Notification.show("Fields cannot be empty");

            }
        });
        backButton.addClickListener(click -> UI.getCurrent().navigate(""));
        add(backButton,brewerNameTF,brewerTypeSelect,saveButton);

    }
}
