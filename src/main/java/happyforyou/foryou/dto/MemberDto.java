package happyforyou.foryou.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import happyforyou.foryou.domain.Note;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@JsonInclude
@Data
public class MemberDto {

    private Long id;

    @NotEmpty
    @NotNull
    private String username;

    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String password;

    private List<Note> notes;

}
