package cn.edu.jlu.examsystem.database.repository;

import cn.edu.jlu.examsystem.database.entity.UserValidationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author WangZeying 2020/9/10 15:17
 */
public interface UserValidateRepository extends CrudRepository<UserValidationEntity, Long> {

    @Query(value = "select 1 as id," +
            "       exists(select 1 from core_user where username=:username) as exist_username, " +
            "       exists(select 1 from core_user where mobile=:mobile) as exist_mobile, " +
            "       exists(select 1 from core_user where email=:email) as exist_email;",
            nativeQuery = true
    )
    UserValidationEntity checkUserInfoDup(@Param("username") String username, String email, String mobile);
}
