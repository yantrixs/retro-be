package com.retro.retrobe.repository;

import com.retro.retrobe.model.MemberCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<MemberCard, Long> {
    List<MemberCard> findByBoardName(String name);
}
