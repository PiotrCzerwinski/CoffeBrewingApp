package com.example.brewing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coffee extends BaseEntity{

    private String roaster;
    private String origin;
    private Integer roastLevel;
    private LocalDate roastDate;
    @ManyToOne
    private User coffeeOwner;
}
