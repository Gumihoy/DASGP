package com.gumihoy.dasgp.admin.web;

import com.gumihoy.dasgp.admin.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kongtong.ouyang on 2018/10/9.
 */
@Controller
@RequestMapping
public class UserController {

    @GetMapping(value = "/login")
    public String toLogin() {
        return "login/login";
    }

    @GetMapping(value = "/logout")
    public String logout() {

        UserUtils.removeUser();

        return "redirect:/login";
    }


}
