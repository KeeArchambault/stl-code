package com.kee.stlcodecafe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="resources")
public class ResourcesController {

    @RequestMapping(value="", method= RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Resources");
        return "ed-resources/index";
    }
}
