package com.example.brewing.ui;

import com.example.brewing.model.*;
import com.example.brewing.repositories.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@UIScope
@Route("user-page")
@Theme(variant = Lumo.DARK)
public class UserPage extends VerticalLayout /*implements BeforeEnterListener*/ {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    BrewerRepository brewerRepository;
    @Autowired
    GrinderRepository grinderRepository;
    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    UserRepository userRepository;

    User activeUser;
    public UserPage(){
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
    }

    Grid<Brewer> brewerGrid = new Grid<>(Brewer.class);
    Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    Grid<Grinder> grinderGrid = new Grid<>(Grinder.class);
    Grid<Coffee> coffeeGrid = new Grid<>(Coffee.class);

    HorizontalLayout headerHL = new HorizontalLayout();
    HorizontalLayout firstHl = new HorizontalLayout();
    HorizontalLayout secondHl = new HorizontalLayout();

    Label brewersLabel = new Label("Your brewers");
    Label recipesLabel = new Label("Your recipies");
    Label grindersLabel = new Label("Your grinders");
    Label coffeeLabel = new Label("Your coffee");

    VerticalLayout brewersVL;
    HorizontalLayout brewersButtonHL;
    VerticalLayout grindersVL;
    HorizontalLayout grindersButtonHL;
    VerticalLayout recipesVL;
    HorizontalLayout recipesButtonHL;
    VerticalLayout coffeeVL;
    HorizontalLayout coffeeButtonHL;

    Button logoutButton = new Button("Logout");
    Button searchButton = new Button("Search recipies");
    Button addBrewerButton = new Button("Add");
    Button deleteBrewerButton = new Button("Delete");
    Button addGrinderButton = new Button("Add");
    Button deleteGrinderButton = new Button("Delete");
    Button addCoffeeButton = new Button("Add");
    Button deleteCoffeeButton = new Button("Delete");
    Button addRecipeButton = new Button("Add");
    Button deleteRecipeButton = new Button("Delete");

    @PostConstruct
    public void init(){
        brewersSetup();
        grindersSetup();
        recipiesSetup();
        coffeeSetup();
        headerHL.setWidthFull();
        headerHL.add(logoutButton,searchButton);
        logoutButton.addClickListener(click->logout());
        searchButton.addClickListener(click->UI.getCurrent().navigate("recipe-search"));
        headerHL.setAlignItems(Alignment.END);
        firstHl.add(brewersVL,grindersVL);
        firstHl.setWidthFull();
        secondHl.add(coffeeVL,recipesVL);
        secondHl.setWidthFull();
        add(headerHL, firstHl, secondHl);
    }

    public void logout(){
        VaadinSession.getCurrent().getSession().setAttribute("user",null);
        activeUser = null;
        UI.getCurrent().navigate("");
    }

    public void brewersSetup(){
        brewersVL= new VerticalLayout();
        brewersButtonHL = new HorizontalLayout();

        brewerGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getBrewerList());
        brewerGrid.setColumns("name","brewerType");
        addBrewerButton.addClickListener(event -> UI.getCurrent().navigate("brewer-form"));

        deleteBrewerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        deleteBrewerButton.addClickListener(click -> deleteBrewer());
        brewersButtonHL.add(addBrewerButton,deleteBrewerButton);
        brewersVL.add(brewersLabel,brewerGrid,brewersButtonHL);
    }

    public void grindersSetup(){
        grindersVL = new VerticalLayout();
        grinderGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getGrinderList());
        grinderGrid.setColumns("manufacturer", "model", "grinderType");
        grindersButtonHL = new HorizontalLayout();

        addGrinderButton.addClickListener(event -> UI.getCurrent().navigate("grinder-form"));
        deleteGrinderButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        deleteGrinderButton.addClickListener(click -> deleteGrinder());

        grindersButtonHL.add(addGrinderButton,deleteGrinderButton);
        grindersVL.add(grindersLabel,grinderGrid,grindersButtonHL);
    }

    public void recipiesSetup(){
        recipesVL = new VerticalLayout();
        recipeGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getRecipeList());
        recipeGrid.setColumns("recipeName","brewer.name");
        recipesButtonHL = new HorizontalLayout();

        addRecipeButton.addClickListener(event -> UI.getCurrent().navigate("recipe-form"));
        deleteRecipeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        deleteRecipeButton.addClickListener(click -> deleteRecipe());
        recipeGrid.addItemDoubleClickListener(click ->{
            VaadinSession.getCurrent().getSession().setAttribute("recipe", click.getItem());

            UI.getCurrent().navigate("recipe-view");
        });

        recipesButtonHL.add(addRecipeButton,deleteRecipeButton);
        recipesVL.add(recipesLabel,recipeGrid,recipesButtonHL);
    }



    public void coffeeSetup(){
        coffeeVL = new VerticalLayout();
        coffeeGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getCoffeeList());
        coffeeGrid.setColumns("roaster","origin","roastLevel","roastDate");
        coffeeButtonHL = new HorizontalLayout();
        addCoffeeButton.addClickListener(event -> UI.getCurrent().navigate("coffee-form"));
        deleteCoffeeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        deleteCoffeeButton.addClickListener(click -> deleteCoffee());
        coffeeButtonHL.add(addCoffeeButton,deleteCoffeeButton);
        coffeeVL.add(coffeeLabel,coffeeGrid,coffeeButtonHL);
    }

    public void deleteBrewer(){
        brewerGrid.getSelectionModel().getFirstSelectedItem()
                .ifPresent(item -> {
                    brewerRepository.deleteById(item.getId());
                    brewerGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getBrewerList());
                });
    }
    public void deleteGrinder(){
        grinderGrid.getSelectionModel().getFirstSelectedItem()
                .ifPresent(item -> {
                    grinderRepository.deleteById(item.getId());
                    grinderGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getGrinderList());
                });
    }
    public void deleteCoffee(){
        coffeeGrid.getSelectionModel().getFirstSelectedItem()
                .ifPresent(item -> {
                    coffeeRepository.deleteById(item.getId());
                    coffeeGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getCoffeeList());
                });
    }
    private void deleteRecipe() {
        recipeGrid.getSelectionModel().getFirstSelectedItem()
                .ifPresent(item -> {
                    recipeRepository.deleteById(item.getId());
                    recipeGrid.setItems(userRepository.findByLoginAndPassword(activeUser.getLogin(),activeUser.getPassword()).get().getRecipeList());
                });
    }
}