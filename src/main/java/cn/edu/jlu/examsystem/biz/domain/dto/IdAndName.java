package cn.edu.jlu.examsystem.biz.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author WangZeying 2020/9/7 23:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class IdAndName<ID> {
//    @NotNull
    private ID id;
    @NotBlank
    private String name;
}
