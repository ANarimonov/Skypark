package com.anarimonov.skypark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "attachment_contents")
public class AttachmentContent {

    public AttachmentContent(byte[] data, Attachment attachment) {
        this.data = data;
        this.attachment = attachment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private byte[] data;
    @OneToOne
    private Attachment attachment;
}
