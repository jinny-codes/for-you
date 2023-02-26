package happyforyou.foryou.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import happyforyou.foryou.domain.CommentStatus;
import happyforyou.foryou.domain.Note;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@JsonInclude
@Data
public class CommentDto {

    private Long id;
    private Note note;
    private String name;
    private String content;
    private CommentStatus commentStatus;

}
