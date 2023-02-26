package happyforyou.foryou.service;

import happyforyou.foryou.domain.Member;
import happyforyou.foryou.domain.Note;
import happyforyou.foryou.repository.MemberRepository;
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
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    @Transactional
    public Member join(Member member) {
        // validateDuplicateMember(member); //중복 회원 검증
        return memberRepository.save(member);
    }

    /*private void validateDuplicateMember(Member member) {
        validateDuplicateEmail(member.getEmail());
        validateDuplicateName(member.getName());
    }

    private void validateDuplicateName(String name) {
        List<Member> findMembers = memberRepository.findByName(name);
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
*/

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long memberId) {

        Optional<Member> result = memberRepository.findById(memberId);
        if (result.isPresent()) { return result.get(); }
        else { throw new MissingResourceException("There is no such member.", "Member", memberId.toString()); }

    }

    @Transactional
    public Member updateMember(Long memberId, Member memberRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MissingResourceException("The member does not exist.", "Member", memberId.toString()));
        member.setName(memberRequest.getName());
        return memberRepository.save(member);
    }

    //delete
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MissingResourceException("The note does not exist.", "Note", memberId.toString()));
        memberRepository.delete(member);
    }
}
