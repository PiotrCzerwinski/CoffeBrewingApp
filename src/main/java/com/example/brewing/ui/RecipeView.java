package com.example.brewing.ui;

import com.example.brewing.model.Recipe;
import com.example.brewing.model.Review;
import com.example.brewing.model.User;
import com.example.brewing.repositories.RecipeRepository;
import com.example.brewing.repositories.ReviewRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Route("recipe-view")
@UIScope
@Theme(variant = Lumo.DARK)
public class RecipeView extends VerticalLayout {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    ReviewRepository reviewRepository;
    User activeUser;
    Recipe activeRecipe;

    public RecipeView(){
        this.activeUser = (User) UI.getCurrent().getSession().getAttribute("user");
        this.activeRecipe = (Recipe) UI.getCurrent().getSession().getAttribute("recipe");
    }

    private Button backButton = new Button("Go back");
    private H1 recipeName;
    private H2 comments;
    private Paragraph recipeTextP;


    @PostConstruct
    public void init(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        backButton.addClickListener(click -> UI.getCurrent().navigate("user-page"));
        recipeName = new H1(activeRecipe.getRecipeName());
        recipeTextP = new Paragraph(activeRecipe.getRecipeText());
        comments = new H2("Reviews:");

        add(backButton,recipeName,recipeTextP,comments);

        if(!recipeRepository.findById(activeRecipe.getId()).get().getReviews().isEmpty()){
            Recipe recipe = recipeRepository.findById(activeRecipe.getId()).get();
            List<Review> reviewsFromRepo = recipe.getReviews();
            reviewsFromRepo.forEach(r ->{
                Text t = new Text(r.getComment());
                add(t);
            });
        } else {
            Text t = new Text("No reviews for this recipe so far.");
            add(t);
        }
    }
}
