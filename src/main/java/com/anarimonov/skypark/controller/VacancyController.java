package com.anarimonov.skypark.controller;

import com.anarimonov.skypark.dto.VacancyDto;
import com.anarimonov.skypark.entity.Vacancy;
import com.anarimonov.skypark.repository.AttachmentRepository;
import com.anarimonov.skypark.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyRepository vacancyRepository;
    private final AttachmentRepository attachmentRepository;

    @GetMapping
    public HttpEntity<?> getAllVacancies() {
        return ResponseEntity.ok(vacancyRepository.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getVacancyById(@PathVariable Long id) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isEmpty())
            return ResponseEntity.status(404).body("Vacancy not found");
        return ResponseEntity.ok(optionalVacancy);
    }

    @PostMapping
    public HttpEntity<?> addVacancy(@RequestBody VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy(vacancyDto.getTitle(), attachmentRepository.findById(vacancyDto.getPhotoId()).orElseThrow(), vacancyDto.getText());
        vacancyRepository.save(vacancy);
        return ResponseEntity.status(201).body("Successfully added!");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editVacancy(@PathVariable Long id, @RequestBody VacancyDto vacancyDto) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isEmpty())
            return ResponseEntity.status(404).body("Vacancy not found!");
        Vacancy vacancy = optionalVacancy.get();
        String title = vacancyDto.getTitle();
        if (title != null)
            vacancy.setTitle(title);
        String text = vacancyDto.getText();
        if (text != null)
            vacancy.setText(text);
        Long photoId = vacancyDto.getPhotoId();
        if (photoId != null)
            vacancy.setPhoto(attachmentRepository.findById(photoId).get());
        vacancyRepository.save(vacancy);
        return ResponseEntity.ok("Successfully updated!");
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        if (!vacancyRepository.existsById(id))
            return ResponseEntity.status(404).body("Vacancy not found!");
        vacancyRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
