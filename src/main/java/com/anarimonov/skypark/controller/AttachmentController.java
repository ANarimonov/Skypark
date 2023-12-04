package com.anarimonov.skypark.controller;

import com.anarimonov.skypark.entity.Attachment;
import com.anarimonov.skypark.entity.AttachmentContent;
import com.anarimonov.skypark.repository.AttachmentContentRepository;
import com.anarimonov.skypark.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttachmentController {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @GetMapping("/{attachmentId}")
    public ResponseEntity<?> getFile(@PathVariable long attachmentId) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isEmpty())
            return ResponseEntity.status(404).body("Attachment not found");
        Attachment attachment = optionalAttachment.get();
        AttachmentContent content = attachmentContentRepository.findByAttachmentId(attachment.getId());
        try {
            ByteArrayResource resource = new ByteArrayResource(content.getData());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + attachment.getName())
                    .contentType(MediaType.valueOf(attachment.getContentType()))
                    .contentLength(content.getData().length)
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping
    public HttpEntity<?> uploadFiles(@RequestPart(value = "file") List<MultipartFile> files) {
        try {
            List<Long> ids = new ArrayList<>();
            for (MultipartFile file : files) {
                Attachment attachment = attachmentRepository.save(new Attachment(
                        file.getOriginalFilename(),
                        file.getSize(),
                        file.getContentType()
                ));
                attachmentContentRepository.save(new AttachmentContent(
                        file.getBytes(),
                        attachment
                ));
                ids.add(attachment.getId());
            }
            return ResponseEntity.status(201).body(ids);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateFile(@PathVariable long id, @RequestPart(value = "file") MultipartFile file) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty()) {
            return ResponseEntity.status(404).body("Attachment not found");
        }
        Attachment attachment = optionalAttachment.get();
        AttachmentContent content = attachmentContentRepository.findByAttachmentId(attachment.getId());
        attachment.setName(file.getName());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        content.setData(file.getBytes());
        attachmentRepository.save(attachment);
        attachmentContentRepository.save(content);
        return ResponseEntity.ok().body("successfully update");
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteFile(@PathVariable long id) {
        attachmentRepository.deleteById(id);
        return ResponseEntity.ok("successfully deleted");
    }
}
