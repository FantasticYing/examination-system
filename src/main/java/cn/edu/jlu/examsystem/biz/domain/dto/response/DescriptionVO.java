package cn.edu.jlu.examsystem.biz.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/10 23:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionVO {
    private Long id;

    /**
     * 0-未分类;1-单选;2-多选;3-判断;5-简答
     */
    private Short typeId;

    @JsonRawValue
    private String description;

}
