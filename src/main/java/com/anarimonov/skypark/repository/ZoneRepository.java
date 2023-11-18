package com.anarimonov.skypark.repository;

import com.anarimonov.skypark.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
