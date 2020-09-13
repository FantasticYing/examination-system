package cn.edu.jlu.examsystem.database.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "question")
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    /**
     * 0-未分类;1-单选;2-多选;3-判断;4-简答
     */
    @Column(name = "type_id", nullable = false)
    private Short typeId;

    @JsonRawValue
    @Column(name = "description_json", nullable = false)
    private String descriptionJson;

    @JsonRawValue
    @Column(name = "answer_json", nullable = false)
    private String answerJson;

    @Column(name = "is_del", nullable = false, columnDefinition = "TINYINT default 0")
    private Boolean isDel;

    @Column(name = "created_time", nullable = true)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = true)
    private LocalDateTime updatedTime;

}
