package com.example.brewing.repositories;

import com.example.brewing.model.Grinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrinderRepository extends JpaRepository<Grinder,Long> {
}
