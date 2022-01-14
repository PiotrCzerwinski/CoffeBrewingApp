package com.example.brewing.ui;

import com.example.brewing.model.*;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.CoffeeRepository;
import com.example.brewing.repositories.GrinderRepository;
import com.example.brewing.repositories.RecipeRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
@UIScope
@Route("")
@Theme(variant = Lumo.DARK)
public class MainPage extends VerticalLayout implements BeforeEnterListener {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    BrewerRepository brewerRepository;
    @Autowired
    GrinderRepository grinderRepository;
    @Autowired
    CoffeeRepository coffeeRepository;

    Grid<Brewer> brewerGrid = new Grid<>(Brewer.class);
    Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    Grid<Grinder> grinderGrid = new Grid<>(Grinder.class);
    Grid<Coffee> coffeeGrid = new Grid<>(Coffee.class);

    HorizontalLayout firstHl = new HorizontalLayout();
    HorizontalLayout secondHl = new HorizontalLayout();

    Label brewersLabel = new Label("Your brewers");
    Label recipiesLabel = new Label("Your recipies");
    Label grindersLabel = new Label("Your grinders");
    Label coffeeLabel = new Label("Your coffee");

    VerticalLayout brewersVL;
    HorizontalLayout brewersButtonHL;
    VerticalLayout grindersVL;
    HorizontalLayout grindersButtonHL;
    VerticalLayout recipiesVL;
    VerticalLayout coffeeVL;

    Button addBrewerButton = new Button("Add");
    Button deleteBrewerButton = new Button("Delete");
    Button addGrinderButton = new Button("Add");
    Button deleteGrinderButton = new Button("Delete");

    @PostConstruct
    public void init(){
        brewersSetup();
        grindersSetup();
        recipiesSetup();
        coffeeSetup();
        firstHl.add(brewersVL,grindersVL);
        firstHl.setWidthFull();
        secondHl.add(coffeeVL,recipiesVL);
        secondHl.setWidthFull();
        add(firstHl, secondHl);
    }
    public void brewersSetup(){
        brewersVL= new VerticalLayout();
        brewersButtonHL = new HorizontalLayout();

        brewerGrid.setItems(brewerRepository.findAll());
        brewerGrid.setColumns("name","brewerType");
        addBrewerButton.addClickListener(event -> UI.getCurrent().navigate("brewer-form"));

        deleteBrewerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        deleteBrewerButton.addClickListener(click -> deleteBrewer());
        brewersButtonHL.add(addBrewerButton,deleteBrewerButton);
        brewersVL.add(brewersLabel,brewerGrid,brewersButtonHL);
    }
    public void grindersSetup(){
        grindersVL = new VerticalLayout();
        grinderGrid.setItems(grinderRepository.findAll());
        grinderGrid.setColumns("manufacturer", "model", "grinderType");
        grindersButtonHL = new HorizontalLayout();
        addGrinderButton.addClickListener(event -> UI.getCurrent().navigate("grinder-form"));
        deleteGrinderButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        deleteGrinderButton.addClickListener(click -> deleteGrinder());
        grindersButtonHL.add(addGrinderButton,deleteGrinderButton);
        grindersVL.add(grindersLabel,grinderGrid,grindersButtonHL);
    }
    public void recipiesSetup(){
        recipiesVL = new VerticalLayout();
        recipeGrid.setItems(recipeRepository.findAll());
        recipeGrid.setColumns("brewer.name","recipeText");
        recipiesVL.add(recipiesLabel,recipeGrid);
    }
    public void coffeeSetup(){
        coffeeVL = new VerticalLayout();
        coffeeGrid.setItems(coffeeRepository.findAll());
        coffeeGrid.setColumns("roaster","origin","roastLevel","roastDate");
        coffeeVL.add(coffeeLabel,coffeeGrid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        brewerGrid.setItems(brewerRepository.findAll());
        recipeGrid.setItems(recipeRepository.findAll());
        grinderGrid.setItems(grinderRepository.findAll());
        coffeeGrid.setItems(coffeeRepository.findAll());
    }

    public void deleteBrewer(){
        brewerGrid.getSelectionModel().getFirstSelectedItem()
                .ifPresent(item -> {
                    recipeRepository.deleteAll(item.getRecipeList());
                    brewerRepository.deleteById(item.getId());
                    brewerGrid.setItems(brewerRepository.findAll());
                    recipeGrid.setItems(recipeRepository.findAll());
                });
    }
    public void deleteGrinder(){
        grinderGrid.getSelectionModel().getFirstSelectedItem()
                .ifPresent(item -> {
                    grinderRepository.deleteById(item.getId());
                    grinderGrid.setItems(grinderRepository.findAll());
                });
    }
}
