package com.anarimonov.skypark.controller;

import com.anarimonov.skypark.dto.SectionDto;
import com.anarimonov.skypark.entity.Section;
import com.anarimonov.skypark.repository.AttachmentRepository;
import com.anarimonov.skypark.repository.SectionRepository;
import com.anarimonov.skypark.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/zone/sections")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SectionController {
    private final SectionRepository sectionRepository;
    private final AttachmentRepository attachmentRepository;
    private final ZoneRepository zoneRepository;

    @GetMapping("/by-zone/{zoneId}")
    public HttpEntity<?> getSectionsByZoneId(@PathVariable long zoneId) {
        List<Section> sections = sectionRepository.findByZoneId(zoneId);
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getSection(@PathVariable long id) {
        Optional<Section> optionalSection = sectionRepository.findById(id);
        if (optionalSection.isEmpty())
            return ResponseEntity.status(404).body("Section not found!");
        return ResponseEntity.ok(optionalSection.get());
    }

    @PostMapping
    public HttpEntity<?> addSection(@RequestBody SectionDto sectionDto) {
        Section section = new Section(sectionDto.getTitle1Uz(), sectionDto.getTitle1Ru(), sectionDto.getTitle2Uz(),
                sectionDto.getTitle2Ru(), attachmentRepository.findAllById(sectionDto.getPhotos()),
                sectionDto.getText(), zoneRepository.findById(sectionDto.getZoneId()).orElseThrow());
        sectionRepository.save(section);
        return ResponseEntity.status(201).body("Successfully added!");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSection(@PathVariable long id, @RequestBody SectionDto sectionDto) {
        Optional<Section> optionalSection = sectionRepository.findById(id);
        if (optionalSection.isEmpty())
            return ResponseEntity.status(404).body("Section not found!");
        Section section = optionalSection.get();
        String title1Uz = sectionDto.getTitle1Uz();
        if (title1Uz != null)
            section.setTitle1Uz(title1Uz);
        String title1Ru = sectionDto.getTitle1Ru();
        if (title1Ru != null)
            section.setTitle1Ru(title1Ru);
        String title2Uz = sectionDto.getTitle2Uz();
        if (title2Uz != null)
            section.setTitle2Uz(title2Uz);
        String title2Ru = sectionDto.getTitle2Ru();
        if (title2Ru != null)
            section.setTitle2Ru(title2Ru);
        List<Long> photos = sectionDto.getPhotos();
        if (photos != null)
            section.setPhotos(attachmentRepository.findAllById(photos));
        String text = sectionDto.getText();
        if (text !=null)
            section.setText(text);
        sectionRepository.save(section);
        return ResponseEntity.ok("Successfully updated!");
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable long id) {
        if (!sectionRepository.existsById(id))
            return ResponseEntity.status(404).body("Section not found!");
        sectionRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
