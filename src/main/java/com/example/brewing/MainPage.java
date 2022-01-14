package com.example.brewing;

import com.example.brewing.model.*;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.CoffeeRepository;
import com.example.brewing.repositories.GrinderRepository;
import com.example.brewing.repositories.RecipeRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
@UIScope
@Route("")
@Theme(variant = Lumo.DARK)
public class MainPage extends VerticalLayout {
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
    VerticalLayout grindersVL;
    VerticalLayout recipiesVL;
    VerticalLayout coffeeVL;

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
        brewerGrid.setItems(brewerRepository.findAll());
        brewerGrid.setColumns("name","brewerType");
        brewersVL.add(brewersLabel,brewerGrid);
    }
    public void grindersSetup(){
        grindersVL = new VerticalLayout();
        grinderGrid.setItems(grinderRepository.findAll());
        grinderGrid.setColumns("manufacturer", "model", "grinderType");
        grindersVL.add(grindersLabel,grinderGrid);
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
}
