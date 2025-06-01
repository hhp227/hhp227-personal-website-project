package kr.hhp227.Webapp.model

import javax.validation.constraints.NotBlank

data class LoginViewModel(
    @NotBlank(message = "사용자 이름 필드가 필요합니다.")
    var username: String? = null,
    @NotBlank(message = "암호 필드가 필요합니다.")
    var password: String? = null,
    var isRememberMe: Boolean = false
)