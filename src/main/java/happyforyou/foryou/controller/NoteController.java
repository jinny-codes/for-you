package happyforyou.foryou.controller;

import happyforyou.foryou.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private NoteRepository noteRepository;

    // 노트 등록
    // 노트 수정
    // 노트 삭제
}
