package com.example.gyankosh.Repository;

import com.example.gyankosh.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
}
