package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "brewer_id", nullable = false)
    private Brewer brewer;
    private String recipeText;

}
