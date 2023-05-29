package com.example.hw4_3.repositories;


import com.example.hw4_3.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Integer> {
    Optional<SessionEntity> findByToken(String token);
}
