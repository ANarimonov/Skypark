package com.anarimonov.skypark.controller;

import com.anarimonov.skypark.dto.EventDto;
import com.anarimonov.skypark.entity.Event;
import com.anarimonov.skypark.repository.AttachmentRepository;
import com.anarimonov.skypark.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final AttachmentRepository attachmentRepository;
    private final EventRepository eventRepository;

    @GetMapping
    public HttpEntity<?> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getEvent(@PathVariable long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty())
            return ResponseEntity.status(404).body("Event not found!");
        return ResponseEntity.ok(optionalEvent.get());
    }

    @PostMapping
    public HttpEntity<?> addEvent(EventDto eventDto) {
        Event event = new Event(eventDto.getTime(), eventDto.getDayOfTheWeek(),
                attachmentRepository.findById(eventDto.getMainPhoto()).orElseThrow(),
                eventDto.getTitle1Uz(), eventDto.getTitle1Ru(), eventDto.getTitle2Uz(),
                eventDto.getTitle2Ru(), eventDto.getTextUz(), eventDto.getTextRu());
        eventRepository.save(event);
        return ResponseEntity.status(201).body("Successfully added!");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editEvent(@PathVariable long id, EventDto eventDto) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty())
            return ResponseEntity.status(404).body("Event not found!");
        Event event = optionalEvent.get();
        String dayOfTheWeek = eventDto.getDayOfTheWeek();
        if (dayOfTheWeek != null)
            event.setDayOfTheWeek(dayOfTheWeek);
        Timestamp time = eventDto.getTime();
        if (time != null)
            event.setTime(time);
        String textUz = eventDto.getTextUz();
        if (textUz != null)
            event.setTextUz(textUz);
        String textRu = eventDto.getTextRu();
        if (textRu != null)
            event.setTextRu(textRu);
        Long mainPhoto = eventDto.getMainPhoto();
        if (mainPhoto != null)
            event.setMainPhoto(attachmentRepository.findById(mainPhoto).orElseThrow());
        String title1Uz = eventDto.getTitle1Uz();
        if (title1Uz != null)
            event.setTitle1Uz(title1Uz);
        String title1Ru = eventDto.getTitle1Ru();
        if (title1Ru != null)
            event.setTitle1Ru(title1Ru);
        String title2Uz = eventDto.getTitle2Uz();
        if (title2Uz != null)
            event.setTitle2Uz(title2Uz);
        String title2Ru = eventDto.getTitle2Ru();
        if (title2Ru!=null)
            event.setTitle2Ru(title2Ru);
        eventRepository.save(event);
        return ResponseEntity.ok("Successfully updated!");
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable long id) {
        eventRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
