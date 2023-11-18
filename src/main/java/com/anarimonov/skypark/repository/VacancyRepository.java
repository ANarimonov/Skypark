package com.anarimonov.skypark.repository;

import com.anarimonov.skypark.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
