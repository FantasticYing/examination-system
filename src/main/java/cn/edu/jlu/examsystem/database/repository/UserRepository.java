package cn.edu.jlu.examsystem.database.repository;

import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wangzeying
 */
public interface UserRepository extends CrudRepository<CoreUserEntity, Long> {

    CoreUserEntity findByUsernameAndPasswordAndIsDel(String username, String password, short isDel);

}
