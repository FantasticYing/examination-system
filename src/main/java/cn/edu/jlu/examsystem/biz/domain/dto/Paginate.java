package cn.edu.jlu.examsystem.biz.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author WangZeying 2020/9/11 9:53
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Paginate {

    private int perPage = 10;
    private int pageNum = 1;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public final int getLimit() {
        return perPage;
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public final int getOffset() {
        return perPage * (pageNum - 1);
    }
}
