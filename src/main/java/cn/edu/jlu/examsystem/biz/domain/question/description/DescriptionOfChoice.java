package cn.edu.jlu.examsystem.biz.domain.question.description;

import cn.edu.jlu.examsystem.biz.domain.dto.IdAndName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author WangZeying 2020/9/8 0:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DescriptionOfChoice extends Description {
    @Valid
    @NotNull
    @Size(min = 2, message = "至少两个选项")
    private List<@NotNull @Valid IdAndName<Short>> options;
}
