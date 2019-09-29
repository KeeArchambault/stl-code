package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {

    @Autowired
    private PostDao postDao;

    @Controller
    @RequestMapping(value="home")
    public class MenuController {



        @RequestMapping(value="")
        public String index(){
            return "home/index";
        }
    }

        @RequestMapping(value="posts")
        public String allPosts(Model model) {

            model.addAttribute("menus", postDao.findAll());
            model.addAttribute("title", "Posts");
            return "menu/index";
        }
}
