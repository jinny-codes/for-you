package happyforyou.foryou.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    private String name;
    private String content;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    @CreatedDate
    private Date timeCreated;

    @LastModifiedDate
    private Date timeModified;

}
