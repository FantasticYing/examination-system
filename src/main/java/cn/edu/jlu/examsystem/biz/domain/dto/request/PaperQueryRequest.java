package cn.edu.jlu.examsystem.biz.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/24 13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperQueryRequest {

    private Long subjectId;

    private boolean myCreated;

    private String keyword;
}
