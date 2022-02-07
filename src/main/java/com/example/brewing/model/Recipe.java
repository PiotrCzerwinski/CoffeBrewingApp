package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe extends BaseEntity{
    private String recipeName;
    @ManyToOne
    private Brewer brewer;
    private String recipeText;
    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Review> reviews;
    @ManyToOne
    private User recipeAuthor;

    public void addReview(Review review){
        this.reviews.add(review);
    }

    @Override
    public String toString() {
        return "Recipe [id= "+this.getId()+
                ", brewer= "+this.getBrewer().getName()+
                ", recipeName= "+this.getRecipeName()+
                "]";

    }
}
