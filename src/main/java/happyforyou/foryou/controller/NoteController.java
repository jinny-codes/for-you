package happyforyou.foryou.controller;

import happyforyou.foryou.domain.Note;
import happyforyou.foryou.dto.CommentDto;
import happyforyou.foryou.dto.NoteDto;
import happyforyou.foryou.repository.NoteRepository;
import happyforyou.foryou.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private NoteRepository noteRepository;
    private NoteService noteService;

    @Autowired
    private ModelMapper modelMapper;

    // 노트 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable(name = "id") Long id) {
        Note note = noteService.getNoteById(id);
        NoteDto noteResponse = modelMapper.map(note, NoteDto.class);
        return ResponseEntity.ok().body(noteResponse);
    }

    // 노트 등록
    @PostMapping
    public ResponseEntity<NoteDto> createPost(@RequestBody NoteDto noteDto) {

        Note noteRequest = modelMapper.map(noteDto, Note.class);
        Note note = noteService.createNote(noteRequest);
        NoteDto noteResponse = modelMapper.map(note, NoteDto.class);
        return new ResponseEntity<NoteDto>(noteResponse, HttpStatus.CREATED);
    }

    // 노트 수정
    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id, @RequestBody NoteDto noteDto) {
        Note noteRequest = modelMapper.map(noteDto, Note.class);
        Note note = noteService.updateNote(id, noteRequest);
        NoteDto noteResponse = modelMapper.map(note, NoteDto.class);
        return ResponseEntity.ok().body(noteResponse);
    }

    // 노트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNote(@PathVariable(name = "id") Long id) {
        noteService.deleteNote(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 노트의 댓글 조회
    @GetMapping("/{id}/comments")
    public List<CommentDto> getCommentsByNote(@PathVariable Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (!noteOptional.isPresent()) {
            throw new MissingResourceException("There is no such note.", "Note", id.toString());
        }

        return noteOptional.get().getComments().stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

    }
}
