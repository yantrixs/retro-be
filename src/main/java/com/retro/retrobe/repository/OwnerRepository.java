package com.retro.retrobe.repository;

import com.retro.retrobe.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // @Query("SELECT o.user_board_owner_id FROM Owner o where o.email = :email")
    // List<Owner> findUserBoardIdsByUserEmail(@Param("email") String emailAddress);
}
