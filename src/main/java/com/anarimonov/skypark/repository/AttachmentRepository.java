package com.anarimonov.skypark.repository;

import com.anarimonov.skypark.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
