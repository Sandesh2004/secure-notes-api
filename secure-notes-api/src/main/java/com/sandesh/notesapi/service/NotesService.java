package com.sandesh.notesapi.service;

import com.sandesh.notesapi.dto.NoteRequest;
import com.sandesh.notesapi.entity.Note;
import com.sandesh.notesapi.entity.User;
import com.sandesh.notesapi.repository.NoteRepository;
import com.sandesh.notesapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotesService {

    private static final Logger log = LoggerFactory.getLogger(NotesService.class);

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public Note createNote(String email, NoteRequest request) {
        log.debug("Creating note for user={}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        Note savedNote = noteRepository.save(note);
        log.info("Note created for user={}, noteId={}", email, savedNote.getId());
        return savedNote;
    }

    public List<Note> getAllNotes(String email) {
        List<Note> notes = noteRepository.findByUserEmail(email);
        log.debug("Fetched {} notes for user={}", notes.size(), email);
        return notes;
    }

    public Note getNoteById(String email, Long noteId) {
        log.debug("Fetching note by id for user={}, noteId={}", email, noteId);
        return noteRepository.findByIdAndUserEmail(noteId, email)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }

    public List<Note> searchNotes(String email, String keyword) {
        log.debug("Searching notes for user={}, keyword={}", email, keyword);
        if (keyword == null || keyword.isBlank()) {
            return getAllNotes(email);
        }

        List<Note> notes = noteRepository.searchOwnedNotes(email, keyword);
        log.debug("Search returned {} notes for user={}", notes.size(), email);
        return notes;
    }

    public Note updateNote(String email, Long noteId, NoteRequest request) {
        log.debug("Updating note for user={}, noteId={}", email, noteId);
        Note note = noteRepository.findByIdAndUserEmail(noteId, email)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note updatedNote = noteRepository.save(note);
        log.info("Note updated for user={}, noteId={}", email, noteId);
        return updatedNote;
    }

    public void deleteNote(String email, Long noteId) {
        log.debug("Deleting note for user={}, noteId={}", email, noteId);
        Note note = noteRepository.findByIdAndUserEmail(noteId, email)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        noteRepository.delete(note);
        log.info("Note deleted for user={}, noteId={}", email, noteId);
    }
}
