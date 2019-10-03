package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="home")
public class HomeController {


    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="")
    public String index(Model model){

        model.addAttribute("title", "Stl Code Cafe");

        return "home/index";
    }

    @RequestMapping(value="cafe-directory", method= RequestMethod.GET)
    public String cafeIndex(Model model) {

        model.addAttribute("title", "Local Cafes");

        return "home/cafe-directory";
    }

    @RequestMapping(value="ed-resources", method= RequestMethod.GET)
    public String resourcesIndex(Model model) {

        model.addAttribute("title", "Resources");
        return "home/ed-resources";
    }
}
