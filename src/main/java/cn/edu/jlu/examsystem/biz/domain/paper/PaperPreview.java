package cn.edu.jlu.examsystem.biz.domain.paper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WangZeying 2020/9/17 16:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaperPreview {
//
//    private String subjectName;
//
//    private String title;
//
//    private String teacherName;

    private Integer score;

//    private Integer duration;

    private List<QuestionView> questions;

}
