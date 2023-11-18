package com.anarimonov.skypark.repository;

import com.anarimonov.skypark.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "delete from admins where username = :username")
    void deleteAdminByUsername(String username);
}
