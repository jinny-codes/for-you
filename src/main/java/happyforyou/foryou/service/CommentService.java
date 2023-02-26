package happyforyou.foryou.service;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.domain.Note;
import happyforyou.foryou.repository.CommentRepository;
import happyforyou.foryou.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    private final NoteRepository noteRepository;

    @Transactional
    public Comment createComment(Long id, Comment comment) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The note does not exist."));
        comment.setNote(note);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, Comment commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("The note does not exist."));
        comment.setContent(commentRequest.getContent());
        comment.setCommentStatus(commentRequest.getCommentStatus());
        return commentRepository.save(comment);
    }

    /*
    public List<Comment> findComments() {
        return commentRepository.findAll();
    }
     */

    public Comment getCommentById(Long commentId) {
        Optional<Comment> result = commentRepository.findById(commentId);
        if (result.isPresent()) { return result.get(); }
        else { throw new IllegalArgumentException("The comment does not exist."); }
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The comment does not exist."));
        commentRepository.delete(comment);
    }
}
