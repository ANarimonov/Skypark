package com.anarimonov.skypark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity(name = "sections")
public class Section {
    public Section(String title1Uz, String title1Ru, String title2Uz, String title2Ru, List<Attachment> photos, String text, Zone zone) {
        this.title1Uz = title1Uz;
        this.title1Ru = title1Ru;
        this.title2Uz = title2Uz;
        this.title2Ru = title2Ru;
        this.photos = photos;
        this.text = text;
        this.zone = zone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title1Uz;
    private String title1Ru;
    private String title2Uz;
    private String title2Ru;
    @OneToMany
    List<Attachment> photos;
    @Column(columnDefinition = "text")
    private String text;
    @OneToOne
    private Zone zone;
}
