package happyforyou.foryou.controller;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.domain.Note;
import happyforyou.foryou.dto.CommentDto;
import happyforyou.foryou.repository.CommentRepository;
import happyforyou.foryou.service.CommentService;
import happyforyou.foryou.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    // 댓글 조회

    // 댓글 등록
    @PostMapping("/notes/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "id") Long id,
                                                    @RequestBody CommentDto commentDto) {
        Comment commentRequest = modelMapper.map(commentDto, Comment.class);
        Comment comment = commentService.createComment(id, commentRequest);
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return new ResponseEntity<CommentDto>(commentResponse, HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/notes/{noteId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long noteId, @PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        Comment commentRequest = modelMapper.map(commentDto, Comment.class);
        Comment comment = commentService.updateComment(commentId, commentRequest);
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return ResponseEntity.ok().body(commentResponse);
    }

    // 댓글 삭제
    @DeleteMapping("/notes/{noteId}/comments/{commentId}")
    public  ResponseEntity<Long> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(commentId, HttpStatus.OK);

    }
}
