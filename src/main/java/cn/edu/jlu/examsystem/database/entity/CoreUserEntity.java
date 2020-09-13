package cn.edu.jlu.examsystem.database.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * @author wangzeying
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "core_user")
public class CoreUserEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String nickname;
  private String email;
  private String mobile;
  private String password;
  private Short roleId;
  private Short isDel;
}
