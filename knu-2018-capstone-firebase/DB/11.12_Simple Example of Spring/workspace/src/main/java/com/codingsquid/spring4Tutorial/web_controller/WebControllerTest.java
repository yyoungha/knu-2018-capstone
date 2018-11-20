package com.codingsquid.spring4Tutorial.web_controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebControllerTest {

    @RequestMapping(value = "/webController.do")
    public String helloWorld(ModelMap modelMap) {
        modelMap.addAttribute("author", "CodingSquid");
        return "index";
    }

}