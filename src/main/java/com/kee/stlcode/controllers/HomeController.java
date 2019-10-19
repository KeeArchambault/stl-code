package com.kee.stlcode.controllers;

import com.kee.stlcode.models.data.PostDao;
import com.kee.stlcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="")
public class HomeController extends AbstractController{


    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="")
    public String index(Model model){

        model.addAttribute("title", "Home");

        return "home/index";
    }


    @RequestMapping(value="resources", method= RequestMethod.GET)
    public String resourcesIndex(Model model) {

        model.addAttribute("title", "Resources");
        return "home/ed-resources";
    }
}
