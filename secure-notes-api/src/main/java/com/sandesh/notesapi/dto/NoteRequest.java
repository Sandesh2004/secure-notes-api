package com.sandesh.notesapi.dto;

import lombok.Data;

@Data
public class NoteRequest {
    private String title;
    private String content;
}
