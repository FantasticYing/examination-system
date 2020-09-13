package cn.edu.jlu.examsystem.biz.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WangZeying 2020/9/13 0:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BatchGet<E> {
    List<E> results;
    Pagination pagination;
}
