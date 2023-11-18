package com.anarimonov.skypark.dto;

import com.anarimonov.skypark.entity.Zone;
import lombok.Getter;

import java.util.List;

@Getter
public class SectionDto {
    private String title1Uz;
    private String title1Ru;
    private String title2Uz;
    private String title2Ru;
    List<Long> photos;
    private String text;
    private Long zoneId;
}
