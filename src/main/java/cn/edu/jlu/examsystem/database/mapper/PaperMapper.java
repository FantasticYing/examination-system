package cn.edu.jlu.examsystem.database.mapper;
import cn.edu.jlu.examsystem.database.entity.PaperEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author WangZeying 2020/9/17 16:36
*/
@Mapper
public interface PaperMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaperEntity record);

    PaperEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaperEntity record);

    List<PaperEntity> query(@Param("condition")QueryCondition condition);

    @NoArgsConstructor
    @Data
    @Builder
    @AllArgsConstructor
    class QueryCondition{

        private Long subjectId;

        private Long teacherId;

        private String keyword;
    }

}