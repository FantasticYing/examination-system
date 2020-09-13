package cn.edu.jlu.examsystem.biz.domain.question.description;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author WangZeying 2020/9/8 0:18
 */
@Data
@Validated
@NoArgsConstructor
public abstract class Description {
    @NotBlank
    @Size(min = 16)
    @Getter
    @Setter
    protected String title;
}
