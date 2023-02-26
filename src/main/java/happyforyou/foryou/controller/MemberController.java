package happyforyou.foryou.controller;

import happyforyou.foryou.domain.Member;
import happyforyou.foryou.dto.NoteDto;
import happyforyou.foryou.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원 등록
    @PostMapping("/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 회원 수정
    @PutMapping("/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    // 회원 탈퇴

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
    public List<NoteDto> getNotesByMember(Long memberId) {
        {
            Optional<Member> userOptional= memberRepository.findById(id);
            if(!userOptional.isPresent())
            {
                throw new UserNotFoundException("id: "+ id);
            }
            return userOptional.get().getPosts();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private String email;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
        private String email;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
