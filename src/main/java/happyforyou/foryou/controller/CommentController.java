package happyforyou.foryou.controller;

import happyforyou.foryou.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private CommentRepository commentRepository;

    // 댓글 등록
    // 댓글 수정
    // 댓글 삭제
}
