package cn.edu.jlu.examsystem.database.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "subject")
@Entity
@DynamicInsert
@Data
public class SubjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Column(name = "is_del", nullable = false, insertable = false, columnDefinition = "tinyint default 0")
    private Short del;

    @Column(name = "created_time", nullable = false, insertable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false, insertable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

}
