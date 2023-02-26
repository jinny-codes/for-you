package happyforyou.foryou.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import happyforyou.foryou.domain.Note;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@JsonInclude
@Data
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private List<Note> notes;

}
