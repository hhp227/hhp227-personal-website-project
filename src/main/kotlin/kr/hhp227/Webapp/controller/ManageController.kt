package kr.hhp227.Webapp.controller

import kr.hhp227.Webapp.model.ChangePasswordViewModel
import kr.hhp227.Webapp.model.IndexViewModel
import kr.hhp227.Webapp.model.User
import kr.hhp227.Webapp.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import javax.validation.Valid

@Controller
@RequestMapping("Manage")
class ManageController(
    private val userService: UserService
) {
    @RequestMapping("")
    fun manage(
        @RequestParam(value = "Message", required = false)
        message: String?,
        modelMap: ModelMap
    ): String {
        val statusMessage = when (message) {
            "ChangePasswordSuccess" -> "Your password has been changed."
            "SetPasswordSuccess" -> "Your password has been set."
            "SetTwoFactorSuccess" -> "Your two-factor authentication provider has been set."
            "Error" -> "An error has occurred."
            "AddPhoneSuccess" -> "Your phone number was added."
            "RemovePhoneSuccess" -> "Your phone number was removed."
            else -> ""
        }

        modelMap.addAttribute(
            "ViewBag",
            mapOf("Title" to "Manage", "StatusMessage" to statusMessage)
        )
        modelMap.addAttribute("IndexViewModel", IndexViewModel(isHasPassword = true, isTwoFactor = false))
        return "manage/index"
    }

    @RequestMapping("ChangePassword")
    fun changePassword(modelMap: ModelMap): String {
        modelMap.addAttribute("ChangePasswordViewModel", ChangePasswordViewModel())
        return "manage/changePassword"
    }

    @RequestMapping("ChangePasswordProcess")
    fun changePasswordProcess(
        @ModelAttribute("ChangePasswordViewModel") model: @Valid ChangePasswordViewModel,
        bindingResult: BindingResult,
        principal: Principal,
        modelMap: ModelMap
    ): String {
        val user: User? = userService.loadUserByUsername(principal.name) as User?

        if (model.newPassword != model.confirmPassword) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.")
        }
        if (bindingResult.hasErrors()) {
            val prioritizedMessages: MutableList<String?>? = getPrioritizedErrors(model) // addErrors
            modelMap.addAttribute("prioritizedErrors", prioritizedMessages)
            modelMap.addAttribute("ChangePasswordViewModel", model)
            return "manage/changePassword"
        }
        if (user != null) {
            val isSuccess: Boolean =
                userService.changePassword(user.username, model.oldPassword, model.newPassword)

            if (isSuccess) {
                return "redirect:/Manage?Message=ChangePasswordSuccess"
            }
        }
        return "manage/changePassword"
    }

    @RequestMapping("ManageLogins")
    fun manageLogins(modelMap: ModelMap): String {
        modelMap.addAttribute(
            "ViewBag",
            mapOf(
                "Title" to "Manage your external logins",
                "StatusMessage" to ""
            )
        )
        return "manage/manageLogins"
    }

    private fun getPrioritizedErrors(model: ChangePasswordViewModel): MutableList<String?> {
        val messages = ArrayList<String?>()

        if (model.oldPassword.isNullOrEmpty()) {
            messages.add("Current password 필드가 필요합니다.")
        }
        if (model.newPassword.isNullOrEmpty()) {
            messages.add("New password 필드가 필요합니다.")
        } else if (model.newPassword!!.length < 6) {
            messages.add("암호은(는) 6자 이상이어야 합니다.")
        }
        if (model.newPassword != model.confirmPassword) {
            messages.add("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.")
        }
        // Incorrect password. 처리
        return messages
    }
}
