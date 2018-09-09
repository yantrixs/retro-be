package com.retro.retrobe.service;

import com.retro.retrobe.model.CardCategory;
import com.retro.retrobe.model.RetroTemplate;
import com.retro.retrobe.payload.Category;
import com.retro.retrobe.payload.TemplateRequest;
import com.retro.retrobe.payload.TemplateResponse;
import com.retro.retrobe.repository.TemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);
    private TemplateRepository repository;
    public TemplateService(final TemplateRepository repository) {
        this.repository = repository;
    }

    public void saveTemplate(TemplateRequest request) {
        RetroTemplate template = new RetroTemplate();
        if(request.getCategories().size() > 0) {
            request.getCategories().forEach(category -> {
                CardCategory cardCategory = new CardCategory();
                cardCategory.setColor(category.getColor());
                cardCategory.setDescription(category.getDescription());
                cardCategory.setName(category.getName());
                template.addCardCategory(cardCategory);
            });
        }

        template.setDescription(request.getDescription());
        template.setImageName(request.getImageName());
        template.setName(request.getName());
        repository.save(template);
    }

    public List<TemplateResponse> getTemplates() {
        List<TemplateResponse> templateResponses = new ArrayList<>();
        for (RetroTemplate bookCategory : repository.findAll()) {
            TemplateResponse response = new TemplateResponse();
            response.setName(bookCategory.getName());
            response.setImageName(bookCategory.getImageName());
            response.setDescription(bookCategory.getDescription());
            bookCategory.getCardCategories().forEach(cardCategory -> {
                Category category = new Category();
                category.setColor(cardCategory.getColor());
                category.setDescription(cardCategory.getDescription());
                category.setName(cardCategory.getName());
                response.addCategory(category);
            });
            response.setCategories(response.getCategories());
            templateResponses.add(response);
        }
        logger.info(templateResponses.toString());
        return templateResponses;
        //return repository.findAll();
    }
}
