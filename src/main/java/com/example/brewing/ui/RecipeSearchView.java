package com.example.brewing.ui;

import com.example.brewing.model.BrewerType;
import com.example.brewing.model.Recipe;
import com.example.brewing.model.Review;
import com.example.brewing.model.User;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.RecipeRepository;
import com.example.brewing.repositories.ReviewRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Route("recipe-search")
@UIScope
@Theme(variant = Lumo.DARK)
public class RecipeSearchView extends VerticalLayout implements BeforeEnterObserver {

        @Autowired
        RecipeRepository recipeRepository;
        @Autowired
        ReviewRepository reviewRepository;
        @Autowired
        BrewerRepository brewerRepository;
        User activeUser;

        public RecipeSearchView(){
            this.activeUser =(User) VaadinSession.getCurrent().getSession().getAttribute("user");
        }

        private Button backButton = new Button("Go back");
        private HorizontalLayout filteringHL;
        private H1 searchH = new H1("Recipes searching");
        private Grid<Recipe> recipeGrid;
        private Select<BrewerType> brewerTypeSelect = new Select<>();
        private Button searchButton = new Button("Search");


        @PostConstruct
        public void init(){
            setSizeFull();
            setAlignItems(Alignment.CENTER);
            createFiltersHeader();
            backButton.addClickListener(click -> UI.getCurrent().navigate("user-page"));

            recipeGrid = new Grid<>();
            recipeGrid.addColumn(Recipe::getRecipeName).setHeader("Recipe name");
            recipeGrid.addColumn(b ->b.getBrewer().getBrewerType()).setHeader("Brewer type");
            recipeGrid.addColumn(r ->r.getReviews().stream().count()).setHeader("Number of reviews");
            recipeGrid.addColumn(a -> a.getRecipeAuthor().getLogin()).setHeader("Author");

            recipeGrid.addItemDoubleClickListener(click->{
                VaadinSession.getCurrent().getSession().setAttribute("recipe",click.getItem());
                UI.getCurrent().navigate("recipe-view");
            });

            searchButton.addClickListener(click -> {
                if(brewerTypeSelect.isEmpty()){
                    recipeGrid.setItems(
                            recipeRepository.findAll().stream()
                                    .filter(o -> !o.getRecipeAuthor().getId().equals(activeUser.getId()))
                                    .collect(Collectors.toList())
                    );
                } else recipeGrid.setItems(getFilteredRecipes());

            });

            add(backButton,searchH,filteringHL,recipeGrid);
        }

        @Override
        public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
            this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
            recipeGrid.setItems(
                    recipeRepository.findAll().stream()
                            .filter(o -> !o.getRecipeAuthor().getId().equals(activeUser.getId()))
                            .collect(Collectors.toList())
            );
        }

        private void createFiltersHeader(){
            filteringHL = new HorizontalLayout();
            filteringHL.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            brewerTypeSelect.setLabel("Brewer type:");
            brewerTypeSelect.setItems(BrewerType.values());
            filteringHL.add(brewerTypeSelect,searchButton);
        }
        private List<Recipe> getFilteredRecipes(){
            return recipeRepository.findAll().stream()
                    .filter(f -> f.getBrewer().getBrewerType().equals(brewerTypeSelect.getValue()))
                    .filter(o -> !o.getRecipeAuthor().equals(activeUser))
                    .collect(Collectors.toList());
        }
}

