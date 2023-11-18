package com.anarimonov.skypark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titleUz;
    private String titleRu;
    @OneToOne
    private Attachment mainVideo;
    @Column(columnDefinition = "text")
    private String descriptionUz;
    @Column(columnDefinition = "text")
    private String descriptionRu;
}
