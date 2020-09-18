package cn.edu.jlu.examsystem.database.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author WangZeying 2020/9/10 14:43
 */
@Data
@Entity
@Table(name = "core_user")
public class UserValidationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    // id仅作标记使用
    private Long id;
    @Transient
    private boolean existUsername;
    @Transient
    private boolean existEmail;
    @Transient
    private boolean existMobile;

    public boolean validated() {
        return !(existEmail || existUsername || existMobile);
    }

}
