package com.retro.retrobe.repository;

import com.retro.retrobe.model.RetroTemplate;
import com.retro.retrobe.payload.TemplateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<RetroTemplate, Long> {
    void saveAndFlush(TemplateRequest request);

    List<RetroTemplate> findAll();
}
