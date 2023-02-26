package happyforyou.foryou.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    private String username;
    private String email;

    private String password;

    @OneToMany(mappedBy = "member", fetch = LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Note> notes;

}
