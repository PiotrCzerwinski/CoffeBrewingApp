package com.example.brewing;

import com.example.brewing.model.*;
import com.example.brewing.repositories.*;
import org.atmosphere.config.service.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
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
    @Autowired
    UserRepository userRepository;


    @PostConstruct
    public void init(){
        User user1 = new User("user","password",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        userRepository.save(user1);

        Grinder xeoleo = new Grinder("Xeoleo","Xeoleo handgrinder", GrinderType.HAND_GRINDER,user1);
        Grinder niche = new Grinder("Niche","Niche Zero", GrinderType.ELECTRIC_GRINDER, user1);
        grinderRepository.saveAll(List.of(xeoleo,niche));

        Brewer aeropress = new Brewer(BrewerType.AEROPRESS,"Aeropress",user1);
        Brewer v60 = new Brewer(BrewerType.POUR_OVER,"Hario V60",user1);
        brewerRepository.saveAll(List.of(aeropress,v60));

        Recipe r1 = new Recipe(v60,"V60 recipe", new ArrayList<>(), user1);
        Recipe r2 = new Recipe(aeropress,"Aeropress recipe",new ArrayList<>(), user1);
        recipeRepository.saveAll(List.of(r1,r2));

        Coffee boyo = new Coffee("Kamerun","Kahawa",3, LocalDate.now().minusDays(14),user1);
        Coffee santos = new Coffee("Brasil","Blue Orca",4, LocalDate.now().minusDays(12),user1);
        coffeeRepository.saveAll(List.of(boyo,santos));


    }
}
