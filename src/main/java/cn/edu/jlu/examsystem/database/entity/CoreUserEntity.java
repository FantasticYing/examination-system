package cn.edu.jlu.examsystem.database.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wangzeying
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "core_user")
@Entity
@DynamicInsert
public class CoreUserEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private String username;
  private String nickname;
  private String email;
  private String mobile;
  private String password;
  private Short roleId;
  private Short isDel;
}
