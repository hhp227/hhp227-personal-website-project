package kr.hhp227.Webapp.controller

import kr.hhp227.Webapp.mapper.HomeMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController(
    private val homeMapper: HomeMapper
) {
    @RequestMapping("/")
    fun home(modelMap: ModelMap): String {
        modelMap.addAttribute("ViewBag", mapOf("Title" to "Home Page"))
        return "home/home"
    }

    @RequestMapping("About")
    fun about(modelMap: ModelMap): String {
        modelMap.addAttribute(
            "ViewBag",
            mapOf(
                "Title" to "About",
                "Message" to "Your application description page."
            )
        )
        return "home/about"
    }

    @RequestMapping("Contact")
    fun contact(modelMap: ModelMap): String {
        modelMap.addAttribute(
            "ViewBag",
            mapOf(
                "Title" to "Contact",
                "Message" to "Your contact page."
            )
        )
        return "home/contact"
    }
}