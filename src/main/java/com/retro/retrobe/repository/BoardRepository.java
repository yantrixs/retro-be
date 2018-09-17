package com.retro.retrobe.repository;

import com.retro.retrobe.model.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<UserBoard, Long> {
    Optional<UserBoard> findByName(String name);
}
