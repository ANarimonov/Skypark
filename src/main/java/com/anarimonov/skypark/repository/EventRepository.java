package com.anarimonov.skypark.repository;

import com.anarimonov.skypark.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EventRepository extends JpaRepository<Event, Long> {

}
