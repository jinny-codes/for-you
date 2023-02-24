package happyforyou.foryou.service;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.domain.CommentStatus;
import happyforyou.foryou.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, String content, CommentStatus commentStatus) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setContent(content);
        comment.setCommentStatus(commentStatus);
    }

    public List<Comment> findComments() {
        return commentRepository.findAll();
    }

    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }

}
