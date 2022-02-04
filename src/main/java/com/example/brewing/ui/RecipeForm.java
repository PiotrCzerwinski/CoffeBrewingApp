package com.example.brewing.ui;

import com.example.brewing.model.Brewer;
import com.example.brewing.model.Coffee;
import com.example.brewing.model.Recipe;
import com.example.brewing.model.User;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.CoffeeRepository;
import com.example.brewing.repositories.RecipeRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
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
import java.util.stream.IntStream;

@Route("recipe-form")
@UIScope
@Theme(variant = Lumo.DARK)
public class RecipeForm extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    BrewerRepository brewerRepository;
    User activeUser;

    public RecipeForm(){
        activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }

    private TextField recipeNameTF = new TextField("Recipe name");
    private TextArea recipeTA = new TextArea("Recipe text");
    private Select<Brewer> brewerSelect = new Select<>();
    private Button saveButton = new Button("Save");
    private Button backButton = new Button("Go back");

    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        brewerSelect.setItems(brewerRepository.findAllByBrewerOwner_Id(activeUser.getId()));
        brewerSelect.setLabel("brewer");
        saveButton.addClickListener(buttonClickEvent -> {
            if(!recipeNameTF.isEmpty() && !recipeTA.isEmpty()
                    && !brewerSelect.isEmpty()) {
                Recipe recipe = new Recipe(recipeNameTF.getValue(),brewerSelect.getValue(),
                        recipeTA.getValue(),new ArrayList<>(),activeUser);
                recipeRepository.save(recipe);
                recipeNameTF.clear();
                brewerSelect.clear();
                recipeTA.clear();
            } else {
                Notification.show("Fields cannot be empty");
            }
        });
        backButton.addClickListener(click -> UI.getCurrent().navigate("user-page"));
        add(backButton,recipeNameTF,brewerSelect,recipeTA,saveButton);

    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
        brewerSelect.setItems(brewerRepository.findAllByBrewerOwner_Id(activeUser.getId()));
    }
}
