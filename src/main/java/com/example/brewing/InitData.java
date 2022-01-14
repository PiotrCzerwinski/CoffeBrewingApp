package com.example.brewing;

import com.example.brewing.model.*;
import com.example.brewing.repositories.BrewerRepository;
import com.example.brewing.repositories.CoffeeRepository;
import com.example.brewing.repositories.GrinderRepository;
import com.example.brewing.repositories.RecipeRepository;
import org.atmosphere.config.service.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitData {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    BrewerRepository brewerRepository;
    @Autowired
    GrinderRepository grinderRepository;
    @Autowired
    CoffeeRepository coffeeRepository;


    @PostConstruct
    public void init(){
        Grinder xeoleo = new Grinder(1L,"Xeoleo","Xeoleo handgrinder", GrinderType.HAND_GRINDER);
        Grinder niche = new Grinder(2L,"Niche","Niche Zero", GrinderType.ELECTRIC_GRINDER);
        grinderRepository.saveAll(List.of(xeoleo,niche));

        Brewer aeropress = new Brewer(1L, BrewerType.AEROPRESS,new ArrayList<>(),"Aeropress");
        Brewer v60 = new Brewer(2L, BrewerType.POUR_OVER,new ArrayList<>(),"Hario V60");
        brewerRepository.saveAll(List.of(aeropress,v60));

        Recipe r1 = new Recipe(1L, v60,"V60 recipe");
        Recipe r2 = new Recipe(2L,aeropress,"Aeropress recipe");
        recipeRepository.saveAll(List.of(r1,r2));

        Coffee boyo = new Coffee(1L,"Kamerun","Kahawa",3, LocalDateTime.now().minusDays(14));
        Coffee santos = new Coffee(2L,"Brasil","Blue Orca",4, LocalDateTime.now().minusDays(12));
        coffeeRepository.saveAll(List.of(boyo,santos));
    }
}
