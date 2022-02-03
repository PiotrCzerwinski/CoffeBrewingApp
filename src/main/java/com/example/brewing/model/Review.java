package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity{
    @ManyToOne
    private Recipe recipe;
    private Long rating;
    private String comment;
    @ManyToOne
    private User author;

    @Override
    public String toString() {
        return "Review [id= "+this.getId()+
                ", recipeName= "+this.getRecipe().getRecipeName()+
                ", rating= "+this.getRating()+
                ", comment= "+this.getComment()+
                "]";

    }
}
