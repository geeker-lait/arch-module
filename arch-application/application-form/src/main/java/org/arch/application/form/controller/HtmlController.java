package org.arch.application.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    @RequestMapping("/")
    public String main(Model model) {
        return "customize_form";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "customize_form";
    }

    @RequestMapping("/customize_form")
    public String customizeForm(Model model) {
        return "customize_form";
    }

    @RequestMapping("/customize_field")
    public String customizeField(Model model) {
        return "customize_field";
    }

    @RequestMapping("/fill_form")
    public String fillForm(Model model) {
        return "fill_form";
    }

//    @RequestMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

}
