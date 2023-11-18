package com.anarimonov.skypark.repository;

import com.anarimonov.skypark.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByZoneId(long zone_id);
}
