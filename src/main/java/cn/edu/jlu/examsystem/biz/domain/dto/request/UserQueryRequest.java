package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.dto.Paginate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/22 15:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserQueryRequest extends Paginate {
    private String name;
    private Short roleId;
}
