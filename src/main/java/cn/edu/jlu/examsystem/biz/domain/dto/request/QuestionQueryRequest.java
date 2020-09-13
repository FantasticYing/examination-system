package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.dto.Paginate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/11 9:25
 */
@Data
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class QuestionQueryRequest extends Paginate {

    private Long subjectId;

    private Long creatorId;

    private Short typeId;

}
