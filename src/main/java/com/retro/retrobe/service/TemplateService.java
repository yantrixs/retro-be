package com.retro.retrobe.service;

import com.retro.retrobe.model.CardCategory;
import com.retro.retrobe.model.RetroTemplate;
import com.retro.retrobe.payload.TemplateRequest;
import com.retro.retrobe.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateService {
    private TemplateRepository repository;
    public TemplateService(final TemplateRepository repository) {
        this.repository = repository;
    }

    public void saveTemplate(TemplateRequest request) {
        RetroTemplate template = new RetroTemplate();
        if(request.getCategories().size() > 0) {
            List<CardCategory> cardCategories = new ArrayList<>();
            request.getCategories().forEach(category -> {
                CardCategory cardCategory = new CardCategory();
                cardCategory.setColor(category.getColor());
                cardCategory.setDescription(category.getDescription());
                cardCategory.setName(category.getName());
                cardCategories.add(cardCategory);
            });
            template.setCardCategories(cardCategories);
        }

        template.setDescription(request.getDescription());
        template.setImageName(request.getImageName());
        template.setName(request.getName());
        repository.save(template);
    }

    public List<RetroTemplate> getTemplates() {
        return repository.findAll();
    }
}
