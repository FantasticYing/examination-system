package cn.edu.jlu.examsystem.biz.domain.question.description;

import cn.edu.jlu.examsystem.biz.common.Validations;
import cn.edu.jlu.examsystem.biz.domain.dto.IdAndName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author WangZeying 2020/9/8 0:18
 */
@Data
@Validated
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Description {
    @NotBlank
    @Size(min = 8)
    protected String title;


    @Valid
    @NotNull(groups = {Validations.ValidateChoice.class, Validations.ValidateMultiAnswerChoice.class})
    @Size(min = 2, message = "至少两个选项", groups = Validations.ValidateChoice.class)
    private List<@NotNull @Valid IdAndName<Short>> options;

}
