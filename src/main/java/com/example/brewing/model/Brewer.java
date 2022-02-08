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
public class Brewer extends BaseEntity{
    private BrewerType brewerType;
    private String name;
    @ManyToOne
    private User brewerOwner;

    @Override
    public String toString() {
        return "Brewer [name= "+this.getName()+
                ", brewerType= "+this.getBrewerType().toString()+
                "]";
    }
}
