package com.anarimonov.skypark.controller;

import com.anarimonov.skypark.dto.ZoneDto;
import com.anarimonov.skypark.entity.Zone;
import com.anarimonov.skypark.repository.AttachmentRepository;
import com.anarimonov.skypark.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/zone")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ZoneController {
    private final ZoneRepository zoneRepository;
    private final AttachmentRepository attachmentRepository;

    @GetMapping
    public HttpEntity<?> getAllZones() {
        return ResponseEntity.ok(zoneRepository.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getZoneById(@PathVariable long id) {
        return ResponseEntity.ok(zoneRepository.findById(id).orElseThrow());
    }

    @PostMapping
    public HttpEntity<?> addZone(@RequestBody ZoneDto zoneDto) {
        Zone zone = new Zone();
        zone.setMainVideo(attachmentRepository.findById(zoneDto.getMainVideoId()).orElseThrow());
        zone.setDescriptionRu(zoneDto.getDescriptionRu());
        zone.setDescriptionUz(zoneDto.getDescriptionUz());
        zone.setTitleUz(zoneDto.getTitleUz());
        zone.setTitleRu(zoneDto.getTitleRu());
        return ResponseEntity.status(201).body("Successfully added!");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editZone(@PathVariable long id, @RequestBody ZoneDto zoneDto) {
        Optional<Zone> optionalZone = zoneRepository.findById(id);
        if (optionalZone.isEmpty())
            return ResponseEntity.status(404).body("Zone not found!");
        Zone zone = optionalZone.get();
        long mainVideoId = zoneDto.getMainVideoId();
        if (mainVideoId != 0)
            zone.setMainVideo(attachmentRepository.findById(mainVideoId).orElseThrow());
        String titleUz = zoneDto.getTitleUz();
        if (titleUz != null)
            zone.setTitleUz(titleUz);
        String titleRu = zoneDto.getTitleRu();
        if (titleRu != null)
            zone.setTitleRu(titleRu);
        String descriptionUz = zoneDto.getDescriptionUz();
        if (descriptionUz != null)
            zone.setDescriptionUz(descriptionUz);
        String descriptionRu = zoneDto.getDescriptionRu();
        if (descriptionRu != null)
            zone.setDescriptionRu(descriptionRu);
        zoneRepository.save(zone);
        return ResponseEntity.ok("Successfully updated!");
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable long id) {
        if (!zoneRepository.existsById(id)) return ResponseEntity.status(404).body("Zone not found!");
        zoneRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
