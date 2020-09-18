package cn.edu.jlu.examsystem.database.repository;

import cn.edu.jlu.examsystem.database.entity.SubjectEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author WangZeying 2020-9-5 20:36
 */
public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {

    @Override
    @Query(value = "select s from SubjectEntity as s where s.del = 0")
    Iterable<SubjectEntity> findAll();
    
    List<SubjectEntity> findAllByNameContains(String name);
}
