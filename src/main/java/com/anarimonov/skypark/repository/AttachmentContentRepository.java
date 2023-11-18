package com.anarimonov.skypark.repository;


import com.anarimonov.skypark.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {
    AttachmentContent findByAttachmentId(Long attachment_id);
}
