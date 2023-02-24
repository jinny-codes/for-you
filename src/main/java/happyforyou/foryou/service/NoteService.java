package happyforyou.foryou.service;

import happyforyou.foryou.domain.Note;
import happyforyou.foryou.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Transactional
    public void saveNote(Note note) {
        noteRepository.save(note);
    }


    @Transactional
    public void updateNote(Long noteId, String title, String description) {
        Note note = noteRepository.findOne(noteId);
        note.setTitle(title);
        note.setDescription(description);
    }

    public List<Note> findNotes() {
        return noteRepository.findAll();
    }

    public Note findOne(Long noteId) {
        return noteRepository.findOne(noteId);
    }
}
