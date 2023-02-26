package happyforyou.foryou.service;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.domain.CommentStatus;
import happyforyou.foryou.domain.Note;
import happyforyou.foryou.repository.CommentRepository;
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
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, Comment commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new MissingResourceException("The comment does not exist.", "Comment", commentId.toString()));
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
        else { throw new MissingResourceException("There is no such comment.", "Comment", commentId.toString()); }
    }

    @Transactional
    public void setPrivate(Long commentId, boolean priv) {
        Comment comment = commentRepository.findOne(commentId);
        if (priv) comment.setCommentStatus(CommentStatus.PRIVATE);
        else comment.setCommentStatus(CommentStatus.PUBLIC);
    }

}
