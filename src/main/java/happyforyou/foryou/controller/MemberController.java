package happyforyou.foryou.controller;

import happyforyou.foryou.domain.Member;
import happyforyou.foryou.dto.CommentDto;
import happyforyou.foryou.dto.MemberDto;
import happyforyou.foryou.dto.NoteDto;
import happyforyou.foryou.repository.MemberRepository;
import happyforyou.foryou.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Autowired
    private final ModelMapper modelMapper;

    // 회원 가입
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Member());

        return "signupForm";
    }

    // 회원 조회
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable(name = "id") Long id) {
        Member member = memberService.getMemberById(id);
        MemberDto memberReponse = modelMapper.map(member, MemberDto.class);
        return ResponseEntity.ok().body(memberReponse);
    }

    // 회원 등록
    @PostMapping("/members")
    public ResponseEntity<MemberDto> saveMember(@RequestBody @Valid MemberDto memberDto) {

        Member memberRequest = modelMapper.map(memberDto, Member.class);
        Member member = memberService.join(memberRequest);
        MemberDto memberResponse = modelMapper.map(member, MemberDto.class);
        return new ResponseEntity<MemberDto>(memberResponse, HttpStatus.CREATED);
    }

    // 회원 수정
    @PutMapping("/members/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable Long id, @RequestBody @Valid MemberDto memberDto) {
        Member memberRequest = modelMapper.map(memberDto, Member.class);
        Member member = memberService.updateMember(id, memberRequest);
        MemberDto memberResponse = modelMapper.map(member, MemberDto.class);
        return ResponseEntity.ok().body(memberResponse);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable(name = "id") Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 회원 전체 조회
    /*@GetMapping("/members")
    public Result members() {

        List<Member> findMembers = memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getEmail()))
                .collect(Collectors.toList());

        return new Result(collect);
    }
     */

    // 회원의 전체 노트 조회
    @GetMapping("/member/{id}/notes")
    public List<NoteDto> getNotesByMember(@PathVariable Long id) {
        Optional<Member> userOptional= memberRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new MissingResourceException("There is no such member.", "Member", id.toString());
        }

        return userOptional.get().getNotes().stream()
                .map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());

    }

}
