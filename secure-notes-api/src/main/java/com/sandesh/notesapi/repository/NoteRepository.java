package com.sandesh.notesapi.repository;

import com.sandesh.notesapi.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    List<Note> findByUserEmail(String email);

        Optional<Note> findByIdAndUserEmail(Long id, String email);

        @Query("""
                        SELECT n FROM Note n
                        WHERE n.user.email = :email
                            AND (
                                        LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                                 OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            )
                        """)
        List<Note> searchOwnedNotes(@Param("email") String email, @Param("keyword") String keyword);
}