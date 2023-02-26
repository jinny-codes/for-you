package happyforyou.foryou.controller;

import happyforyou.foryou.domain.Comment;
import happyforyou.foryou.dto.CommentDto;
import happyforyou.foryou.repository.CommentRepository;
import happyforyou.foryou.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private CommentRepository commentRepository;
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    // 댓글 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") Long id) {
        Comment comment = commentService.getCommentById(id);
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return ResponseEntity.ok().body(commentResponse);
    }

    // 댓글 등록
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        Comment commentRequest = modelMapper.map(commentDto, Comment.class);
        Comment comment = commentService.createComment(commentRequest);
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return new ResponseEntity<CommentDto>(commentResponse, HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Comment commentRequst = modelMapper.map(commentDto, Comment.class);
        Comment comment = commentService.updateComment(id, commentRequst);
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return ResponseEntity.ok().body(commentResponse);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public  ResponseEntity<Long> deleteComment(@PathVariable(name = "id") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
