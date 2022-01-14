package com.example.brewing;

import com.example.brewing.model.Brewer;
import com.example.brewing.model.Grinder;
import com.example.brewing.model.GrinderType;
import com.example.brewing.model.Recipe;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.GrinderRepository;
import com.example.brewing.repositories.RecipeRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
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

    Grid<Brewer> brewerGrid = new Grid<>(Brewer.class);
    Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    Grid<Grinder> grinderGrid = new Grid<>(Grinder.class);

    Label brewersLabel = new Label("Your brewers");
    Label recipiesLabel = new Label("Your recipies");
    Label grindersLabel = new Label("Your grinders");

    @PostConstruct
    public void init(){
        brewersSetup();
        grindersSetup();
        recipiesSetup();

        add(brewersLabel, brewerGrid, grindersLabel,grinderGrid, recipiesLabel, recipeGrid);
    }
    public void brewersSetup(){
        brewerGrid.setItems(brewerRepository.findAll());
        brewerGrid.setColumns("name","brewerType");
    }
    public void grindersSetup(){
        grinderGrid.setItems(grinderRepository.findAll());
        grinderGrid.setColumns("manufacturer", "model", "grinderType");
    }
    public void recipiesSetup(){
        recipeGrid.setItems(recipeRepository.findAll());
        recipeGrid.setColumns("brewer.name","recipeText");
    }
}
