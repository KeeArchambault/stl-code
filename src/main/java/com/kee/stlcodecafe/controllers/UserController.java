package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @RequestMapping(value="")
    public String login(Model model){
        return "profile/login";
    }

    @RequestMapping(value="sign-up")
    public String signUp(Model model){
        return "profile/sign-up";
    }

}
