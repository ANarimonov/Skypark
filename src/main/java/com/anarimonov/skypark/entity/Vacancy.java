package com.anarimonov.skypark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity(name = "vacancies")
public class Vacancy {
    public Vacancy(String title, Attachment photo, String text) {
        this.title = title;
        this.photo = photo;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToOne
    private Attachment photo;
    @Column(columnDefinition = "text")
    private String text;
    @CreationTimestamp
    private Timestamp time;
}
