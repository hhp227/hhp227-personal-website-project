package kr.hhp227.Webapp.controller

import kr.hhp227.Webapp.model.LoginViewModel
import kr.hhp227.Webapp.model.RegisterViewModel
import kr.hhp227.Webapp.model.User
import kr.hhp227.Webapp.service.UserService
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Controller
class AccountController(
    private val userService: UserService
) {
    @RequestMapping("Login")
    fun login(modelMap: ModelMap): String {
        modelMap.addAttribute("ViewBag", mapOf("Title" to "로그인"))
        modelMap.addAttribute("LoginViewModel", LoginViewModel())
        return "account/login"
    }

    @RequestMapping("Register")
    fun register(modelMap: ModelMap): String {
        modelMap.addAttribute("ViewBag", mapOf("Title" to "등록"))
        modelMap.addAttribute("RegisterViewModel", RegisterViewModel())
        return "account/register"
    }

    @RequestMapping("RegisterProcess")
    fun registerProcess(
        @Valid
        @ModelAttribute("RegisterViewModel")
        model: RegisterViewModel,
        bindingResult: BindingResult,
        user: User
    ): String {
        println("registerProcess: " + model)
        user.setAccountNonExpired(true)
        user.setAccountNonLocked(true)
        user.setCredentialsNonExpired(true)
        user.setEnabled(true)
        user.setAuthorities(AuthorityUtils.createAuthorityList("USER"))
        if (model.password != model.confirmPassword) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.")
        }

        if (bindingResult.hasErrors()) {
            return "account/register"
        }



        userService.registerUser(user)
        return "redirect:/"
    }

    // 임시 로그아웃
    // post요청에 csrf보내야함?
    @RequestMapping("/Account/LogOff")
    fun logout(request: HttpServletRequest?, response: HttpServletResponse?): String {
        SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().authentication)
        return "redirect:/"
    }
}
