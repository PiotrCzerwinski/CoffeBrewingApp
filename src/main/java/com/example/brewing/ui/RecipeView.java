package com.example.brewing.ui;

import com.example.brewing.model.Recipe;
import com.example.brewing.model.Review;
import com.example.brewing.model.User;
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
import com.vaadin.flow.component.textfield.TextArea;
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
import java.util.Collection;
import java.util.List;

@Route("recipe-view")
@UIScope
@Theme(variant = Lumo.DARK)
public class RecipeView extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    ReviewRepository reviewRepository;
    User activeUser;
    Recipe activeRecipe;

    public RecipeView(){
        this.activeUser =(User) VaadinSession.getCurrent().getSession().getAttribute("user");
        this.activeRecipe = (Recipe) VaadinSession.getCurrent().getSession().getAttribute("recipe");
    }

    private Button backButton = new Button("Go back");
    private H1 recipeName;
    private H2 comments;
    private Grid<Review> reviewsGrid;
    private Paragraph recipeTextP;

    private HorizontalLayout commentHl;
    private TextArea comment;
    private Select<Long> rating;


    @PostConstruct
    public void init(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        backButton.addClickListener(click -> UI.getCurrent().navigate("recipe-search"));
        recipeName = new H1(activeRecipe.getRecipeName());
        recipeTextP = new Paragraph(activeRecipe.getRecipeText());
        comments = new H2("Reviews:");

        reviewsGrid = new Grid<>();
        reviewsGrid.addColumn(Review::getComment).setHeader("Review");
        reviewsGrid.addColumn(Review::getRating).setHeader("Rating");
        reviewsGrid.addColumn(a -> a.getAuthor().getLogin()).setHeader("Author");
        reviewsGrid.setItems(activeRecipe.getReviews());
        createCommentHl();
        add(backButton,recipeName,recipeTextP,comments,commentHl,reviewsGrid);
    }

    private void createCommentHl(){
        commentHl = new HorizontalLayout();
        comment = new TextArea("Review");
        rating = new Select<>();
        rating.setItems(List.of(1L,2L,3L,4L,5L));
        rating.setLabel("Your rating");
        Button postButton = new Button("Post");
        postButton.addClickListener(click -> postReview());
        commentHl.add(comment,rating,postButton);
    }
    private void postReview(){
        if(!comment.isEmpty() && !rating.isEmpty()){
            Review review = new Review(activeRecipe,rating.getValue(),
                    comment.getValue(), activeUser);
            reviewRepository.save(review);


            activeRecipe.addReview(review);
            recipeRepository.save(activeRecipe);
            comment.clear();
            rating.clear();
            reviewsGrid.setItems(recipeRepository.findById(activeRecipe.getId()).get().getReviews());
        }else Notification.show("Fields cannot be empty");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.activeUser = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
        this.activeRecipe = (Recipe) VaadinSession.getCurrent().getSession().getAttribute("recipe");
    }
}
