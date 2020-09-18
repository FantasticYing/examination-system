
package cn.edu.jlu.examsystem.database.mapper;

import cn.edu.jlu.examsystem.database.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface QuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuestionEntity record);

    int insertSelective(QuestionEntity record);

    QuestionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionEntity record);

    int updateByPrimaryKey(QuestionEntity record);

    List<QuestionEntity> findAllByIds(@Param("idCollection") Collection<Long> idCollection);

    List<QuestionEntity> query(@Param("condition") QueryCondition condition);

    List<QuestionEntity> random(@Param("condition") QueryCondition condition);

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class QueryCondition {
        private Long subjectId;
        private Long creatorId;
        private Short typeId;
        private String keyword;
        private Long limit;
        private Long offset;
    }
}
