package happyforyou.foryou.controller;

import happyforyou.foryou.domain.Member;
import happyforyou.foryou.dto.LoginDto;
import happyforyou.foryou.dto.SignUpDto;
import happyforyou.foryou.repository.MemberRepository;
import happyforyou.foryou.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed in successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        if (memberRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
        }

        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }

        Member member = new Member();
        member.setUsername(signUpDto.getUsername());
        member.setEmail(signUpDto.getEmail());
        member.setPassword(signUpDto.getPassword());

        memberRepository.save(member);

        return new ResponseEntity<>("Member registered successfully!", HttpStatus.OK);
    }
}
