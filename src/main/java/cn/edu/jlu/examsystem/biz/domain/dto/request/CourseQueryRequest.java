package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.dto.Paginate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/14 22:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CourseQueryRequest extends Paginate {

    private Long subjectId;

    private boolean myCreated;

    private boolean myTaught;

    private boolean myChosen;

}
