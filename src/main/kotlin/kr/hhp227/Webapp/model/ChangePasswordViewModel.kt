package kr.hhp227.Webapp.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ChangePasswordViewModel(
    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    var oldPassword: String? = null,
    @NotBlank(message = "새 비밀번호를 입력하세요.")
    @Size(min = 6, message = "새 비밀번호는 최소 6자 이상이어야 합니다.")
    var newPassword: String? = null,
    @NotBlank(message = "새 비밀번호 확인을 입력하세요.")
    var confirmPassword: String? = null
)