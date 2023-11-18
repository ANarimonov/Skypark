package com.anarimonov.skypark.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class EventDto {
    private Timestamp time;
    private String dayOfTheWeek;
    private Long mainPhoto;
    private String title1Uz;
    private String title1Ru;
    private String title2Uz;
    private String title2Ru;
    private String textUz;
    private String textRu;
}
