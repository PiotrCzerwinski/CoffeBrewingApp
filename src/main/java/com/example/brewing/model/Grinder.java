package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grinder extends BaseEntity{

    private String manufacturer;
    private String model;
    private GrinderType grinderType;
    @ManyToOne
    private User grinderOwner;

    @Override
    public String toString() {
        return "Grinder [id= "+this.getId()+
                ", manufacturer= "+this.getManufacturer()+
                ", model= "+this.getModel()+
                ", grinderType= "+this.getGrinderType().toString()+
                "]";

    }
}
