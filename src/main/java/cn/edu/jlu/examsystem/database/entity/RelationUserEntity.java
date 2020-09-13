package cn.edu.jlu.examsystem.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/13 19:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelationUserEntity {
    private Long id;

    private Long courseId;

    private Long userId;

    private String userName;
}