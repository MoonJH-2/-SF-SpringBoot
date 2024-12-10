package com.moon.demo01.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
/* UserRequest를 로그인에 사용하지 않고 LoginRequest를 사용함.
 *   1. 필드 과다
 *   2. 검증 규칙 충돌
 *   3. 의미적 부정확성
 */