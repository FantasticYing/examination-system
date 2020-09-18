package cn.edu.jlu.examsystem.biz.domain.paper;

import cn.edu.jlu.examsystem.biz.domain.dto.response.DescriptionVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WangZeying 2020/9/17 18:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionView {
    private Short typeId;
    private Integer score;
    private List<@NotNull DescriptionVO> descriptions;
}
