package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    private String login;
    private String password;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Brewer> brewerList;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Grinder> grinderList;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Recipe> recipeList;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Coffee> coffeeList;
}
