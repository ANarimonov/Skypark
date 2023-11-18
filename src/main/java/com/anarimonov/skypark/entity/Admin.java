package com.anarimonov.skypark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "admins")
public class Admin{
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role = "ROLE_ADMIN";

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
