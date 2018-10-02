package com.retro.retrobe.repository;

import com.retro.retrobe.model.BoardMember;
import com.retro.retrobe.model.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<UserBoard, Long> {
    Optional<UserBoard> findByName(String name);
    Boolean existsBoardByName(String boardName);
    @Modifying
    @Query("Update UserBoard board SET board.boardMembers=:member WHERE board.name=:name")
    void saveBoardByName(@Param("name") String name, @Param("member") BoardMember member);
}
