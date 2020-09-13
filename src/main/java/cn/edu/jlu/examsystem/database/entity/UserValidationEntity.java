package cn.edu.jlu.examsystem.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author WangZeying 2020/9/10 14:43
 */
@Data
@Entity
@Table(name = "core_user")
public class UserValidationEntity {
    @Id
    // id仅作标记使用
    private Long id;
    private boolean existUsername;
    private boolean existEmail;
    private boolean existMobile;

    public boolean validated() {
        return !(existEmail || existUsername || existMobile);
    }

}
