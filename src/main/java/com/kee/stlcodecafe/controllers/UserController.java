package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String profile (Model model, @PathVariable int id){

        model.addAttribute("user", userDao.findById(id));
        model.addAttribute("title", "Profile");
        return "profile/index";
    }

    @RequestMapping(value="/login")
    public String login(Model model){

        model.addAttribute("title", "Log In");
        return "profile/login";
    }

    @RequestMapping(value="sign-up", method = RequestMethod.GET)
    public String signUp(Model model, User user){

        model.addAttribute("user", user);
        model.addAttribute("title", "Sign Up");
        return "profile/sign-up";
    }

    @RequestMapping(value="sign-up", method= RequestMethod.POST)
    public String processSignUp(Model model, @ModelAttribute @Valid User user, Errors errors) {

        if (errors.hasErrors() || user.getPassword() != user.getVerify()) {

            return "user/sign-up";

        } else {
            userDao.save(user);
            int id = user.getId();

            return "redirect:/profile" + id;
        }
    }




}

