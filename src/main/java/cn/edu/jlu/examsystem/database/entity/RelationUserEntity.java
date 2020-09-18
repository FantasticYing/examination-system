package cn.edu.jlu.examsystem.database.entity;

import lombok.*;

/**
 * @author WangZeying 2020/9/13 19:35
 */
@Data
@Builder
@EqualsAndHashCode(of = {"courseId", "userId"})
@AllArgsConstructor
@NoArgsConstructor
public class RelationUserEntity {
    private Long id;

    private Long courseId;

    private Long userId;

    private String userName;
}