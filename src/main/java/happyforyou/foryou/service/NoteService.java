package happyforyou.foryou.service;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.domain.Note;
import happyforyou.foryou.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Transactional
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Transactional
    public Note updateNote(Long noteId, Note noteRequest) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("The note does not exist."));
        note.setTitle(noteRequest.getTitle());
        note.setDescription(noteRequest.getDescription());
        return noteRepository.save(note);
    }

    @Transactional
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("The note does not exist."));
        noteRepository.delete(note);
    }

    public List<Note> findNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long noteId) {
        Optional<Note> result = noteRepository.findById(noteId);
        if (result.isPresent()) { return result.get(); }
        else { throw new IllegalArgumentException("The note does not exist."); }
    }

    public List<Comment> getCommentsByNote(Long noteId) {
        Optional<Note> result = noteRepository.findById(noteId);
        if (result.isPresent()) {
            return result.get().getComments();
        }
        else {
            throw new IllegalArgumentException("The note does not exist.");
        }
    }


}
