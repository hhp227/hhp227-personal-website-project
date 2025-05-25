package kr.hhp227.Webapp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ManageController {
    @RequestMapping("Manage")
    fun manage(modelMap: ModelMap): String {
        modelMap.addAttribute("ViewBag", mapOf("Title" to "Manage"))
        return "manage/index"
    }

    @RequestMapping("Manage/ChangePassword")
    fun changePassword(modelMap: ModelMap): String {
        modelMap.addAttribute("ViewBag", mapOf("Title" to "Change Password"))
        return "manage/changePassword"
    }


    @RequestMapping("Manage/ManageLogins")
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
