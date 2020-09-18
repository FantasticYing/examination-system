package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.database.entity.QuestionEntity;
import cn.edu.jlu.examsystem.http.config.JacksonConfig;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @JsonDeserialize(using = JacksonConfig.RawValueDeserializer.class )
    private String description;

    public static DescriptionVO fromEntity(QuestionEntity entity) {
        return DescriptionVO.builder()
                .id(entity.getId())
                .typeId(entity.getTypeId())
                .description(entity.getDescriptionJson())
                .build();
    }
}
