package kr.hhp227.Webapp.model

data class IndexViewModel(
    var isHasPassword: Boolean = false,
    var logins: MutableList<String?>? = null,
    var phoneNumber: String? = null,
    var isTwoFactor: Boolean = false,
    var isBrowserRemembered: Boolean = false
)