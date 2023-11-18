package com.anarimonov.skypark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity(name = "events")
public class Event {
    public Event(Timestamp time, String dayOfTheWeek, Attachment mainPhoto, String title1Uz, String title1Ru, String title2Uz, String title2Ru, String textUz, String textRu) {
        this.time = time;
        this.dayOfTheWeek = dayOfTheWeek;
        this.mainPhoto = mainPhoto;
        this.title1Uz = title1Uz;
        this.title1Ru = title1Ru;
        this.title2Uz = title2Uz;
        this.title2Ru = title2Ru;
        this.textUz = textUz;
        this.textRu = textRu;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp time;
    private String dayOfTheWeek;
    @OneToOne
    private Attachment mainPhoto;
    private String title1Uz;
    private String title1Ru;
    private String title2Uz;
    private String title2Ru;
    private String textUz;
    private String textRu;
}
