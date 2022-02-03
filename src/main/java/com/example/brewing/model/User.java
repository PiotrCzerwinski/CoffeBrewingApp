package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    private String login;
    private String password;
    @OneToMany(mappedBy = "brewerOwner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Brewer> brewerList;
    @OneToMany(mappedBy = "grinderOwner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Grinder> grinderList;
    @OneToMany(mappedBy = "recipeAuthor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Recipe> recipeList;
    @OneToMany(mappedBy = "coffeeOwner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Coffee> coffeeList;
}
