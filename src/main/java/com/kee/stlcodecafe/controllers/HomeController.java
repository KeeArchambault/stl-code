package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="home")
public class HomeController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

        @RequestMapping(value="")
        public String index(Model model){

            model.addAttribute("title", "Home");

            return "home/index";
        }

        @RequestMapping(value="forum")
        public String allPosts(Model model) {

            model.addAttribute("posts", postDao.findAll());
            model.addAttribute("title", "Posts");
            return "forum/index";
        }
}
