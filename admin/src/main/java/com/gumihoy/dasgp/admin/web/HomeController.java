package com.gumihoy.dasgp.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kongtong.ouyang on 2018/10/10.
 */
@Controller
@RequestMapping
public class HomeController {

    @GetMapping(value = "")
    public String toHome() {
        return "home/home";
    }

}
