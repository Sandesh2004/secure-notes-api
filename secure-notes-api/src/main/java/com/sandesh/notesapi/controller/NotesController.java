package com.sandesh.notesapi.controller;

import com.sandesh.notesapi.dto.NoteRequest;
import com.sandesh.notesapi.entity.Note;
import com.sandesh.notesapi.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {

    private static final Logger log = LoggerFactory.getLogger(NotesController.class);

    private final NotesService notesService;

    @PostMapping
    public Note create(@AuthenticationPrincipal UserDetails principal,
                       @RequestBody NoteRequest request) {
        log.info("Create note request received for user={}", principal.getUsername());
        return notesService.createNote(principal.getUsername(), request);
    }

    @GetMapping
    public List<Note> getAll(@AuthenticationPrincipal UserDetails principal) {
        log.debug("Get all notes request for user={}", principal.getUsername());
        return notesService.getAllNotes(principal.getUsername());
    }

    @GetMapping("/{id}")
    public Note getById(@AuthenticationPrincipal UserDetails principal,
                        @PathVariable Long id) {
        log.debug("Get note by id request for user={}, noteId={}", principal.getUsername(), id);
        return notesService.getNoteById(principal.getUsername(), id);
    }

    @GetMapping("/search")
    public List<Note> search(@AuthenticationPrincipal UserDetails principal,
                             @RequestParam String keyword) {
        log.debug("Search notes request for user={}, keyword={}", principal.getUsername(), keyword);
        return notesService.searchNotes(principal.getUsername(), keyword);
    }

    @PutMapping("/{id}")
    public Note update(@AuthenticationPrincipal UserDetails principal,
                       @PathVariable Long id,
                       @RequestBody NoteRequest request) {
        log.info("Update note request for user={}, noteId={}", principal.getUsername(), id);
        return notesService.updateNote(principal.getUsername(), id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@AuthenticationPrincipal UserDetails principal,
                         @PathVariable Long id) {
        log.info("Delete note request for user={}, noteId={}", principal.getUsername(), id);
        notesService.deleteNote(principal.getUsername(), id);
        return "Note deleted successfully";
    }
}