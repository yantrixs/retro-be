package com.retro.retrobe.repository;

import com.retro.retrobe.model.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<BoardMember, Long> {
}
