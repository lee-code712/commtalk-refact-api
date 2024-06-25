package com.commtalk.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "로그인 DTO")
public class LoginDTO {

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Schema(description = "닉네임")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Schema(description = "비밀번호")
    private String password;

}
