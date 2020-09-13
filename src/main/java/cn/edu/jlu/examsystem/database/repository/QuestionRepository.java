package cn.edu.jlu.examsystem.database.repository;

import cn.edu.jlu.examsystem.database.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>, JpaSpecificationExecutor<QuestionEntity> {

    QuestionEntity findByIdAndIsDel(Long id, boolean isDel);



}