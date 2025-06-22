package kr.hhp227.Webapp.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterViewModel(
    @NotBlank(message = "사용자 이름 필드가 필요합니다.")
    val username: String? = null,
    @NotBlank(message = "암호 필드가 필요합니다.")
    @Size(min = 6, message = "암호은(는) 6자 이상이어야 합니다.")
    val password: String? = null,
    @NotBlank
    val confirmPassword: String? = null
)