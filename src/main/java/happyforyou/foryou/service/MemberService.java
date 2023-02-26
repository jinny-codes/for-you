package happyforyou.foryou.service;

import happyforyou.foryou.domain.Member;
import happyforyou.foryou.domain.Note;
import happyforyou.foryou.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
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
        member.setUsername(memberRequest.getUsername());
        return memberRepository.save(member);
    }

    //delete
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MissingResourceException("The note does not exist.", "Note", memberId.toString()));
        memberRepository.delete(member);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with name or email: " + usernameOrEmail));
        Set<GrantedAuthority> authorities = member
                .getNotes()
                .stream()
                .map((note) -> new SimpleGrantedAuthority(note.getTitle())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(member.getEmail(),
                member.getPassword(),
                authorities);
    }
}
