package com.example.brewing.repositories;

import com.example.brewing.model.Brewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrewerRepository extends JpaRepository<Brewer,Long> {
    List<Brewer> findAllByBrewerOwner_Id(Long id);
}
