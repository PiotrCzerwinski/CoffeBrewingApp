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
    @ManyToOne
    private Brewer brewer;
    private String recipeText;
    @OneToMany
    private List<Review> reviews;
    @ManyToOne
    private User recipeAuthor;

}
