package cn.edu.jlu.examsystem.biz.domain.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * @author WangZeying 2020/9/7 0:42
 */
@Data
@NoArgsConstructor
public class UserRegisterRequest {

    @NotNull(message = "用户名不可为空")
    @NotBlank(message = "用户名不可为空")
    @Size(min = 6, max = 32, message = "用户名长度6-32字符")
    private String username;
    @Size(max = 256, message = "用户名长度6-32字符")
    private String nickname;
    @Email
    private String email;
    @Size(min = 11, max = 32, message = "手机号长度11-32字符")
    private String mobile;
    @Size(min = 6, max = 32, message = "密码长度6-32字符")
    private String password;
    @Min(1)
    @Max(3)
    private Short roleId;
    @NotBlank(message = "邀请码不可为空")
    private String secret;

}
