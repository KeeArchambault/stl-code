package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="forum")
public class ForumController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="", method= RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "Forum");
        return "forum/index";
    }
}
