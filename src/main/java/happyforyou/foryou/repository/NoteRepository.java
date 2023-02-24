package happyforyou.foryou.repository;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.domain.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteRepository {

    private final EntityManager em;

    public void save(Note note) {
        if (note.getId() == null) {
            em.persist(note);
        } else {
            em.merge(note);
        }
    }

    public Note findOne(Long id) {
        return em.find(Note.class, id);
    }

    public List<Note> findAll() {
        return em.createQuery("select i from Item i", Note.class)
                .getResultList();
    }
}
