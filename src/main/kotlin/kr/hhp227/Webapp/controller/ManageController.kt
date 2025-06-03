package kr.hhp227.Webapp.controller

import kr.hhp227.Webapp.model.ChangePasswordViewModel
import kr.hhp227.Webapp.model.IndexViewModel
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("Manage")
class ManageController {
    @RequestMapping("")
    fun manage(
        @RequestParam(value = "Message", required = false)
        message: String?,
        modelMap: ModelMap
    ): String {
        val statusMessage = when {
            message == "ChangePasswordSuccess" -> "Your password has been changed."
            message == "SetPasswordSuccess" -> "Your password has been set."
            message == "SetTwoFactorSuccess" -> "Your two-factor authentication provider has been set."
            message == "Error" -> "An error has occurred."
            message == "AddPhoneSuccess" -> "Your phone number was added."
            message == "RemovePhoneSuccess" -> "Your phone number was removed."
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
        modelMap.addAttribute("ViewBag", mapOf("Title" to "Change Password"))
        modelMap.addAttribute("ChangePasswordViewModel", ChangePasswordViewModel())
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
}
