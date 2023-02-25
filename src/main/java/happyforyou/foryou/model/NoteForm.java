package happyforyou.foryou.model;

import happyforyou.foryou.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class NoteForm {

    @NotEmpty(message = "제목은 필수입니다")
    private String title;
    private String description;

}
