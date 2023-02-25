package happyforyou.foryou.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import happyforyou.foryou.domain.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude
@Data
public class NoteDto {

    private Long id;
    private String title;
    private String description;
    private List<Comment> comments;

}
