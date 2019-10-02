package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="user")
public class UserController {



    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String login(Model model){

        model.addAttribute("title", "Log In");
        return "profile/login";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String name) {

        for (User user : userDao.findAll()) {
            if (user.getName().equals(name)) {
                int id = user.getId();
                model.addAttribute("user", user);
                model.addAttribute("title", "Log In");
                return "redirect:user/profile/" + id;
            }

        }
            model.addAttribute("title", "Log In");
            return "profile/login";
    }

    @RequestMapping(value = "profile/{id}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable int id){

        model.addAttribute("user", userDao.findById(id));
        model.addAttribute("title", "Profile");
        return "profile/index";
    }

    @RequestMapping(value="sign-up", method = RequestMethod.GET)
    public String signUp(Model model, User user){

        model.addAttribute("user", user);
        model.addAttribute("title", "Sign Up");
        return "profile/sign-up";
    }

    @RequestMapping(value="sign-up", method= RequestMethod.POST)
    public String processSignUp(Model model, @ModelAttribute @Valid User user, Errors errors) {

        if (errors.hasErrors() || !user.getPassword().equals(user.getVerify())) {

            return "profile/sign-up";

        } else {
            userDao.save(user);
            int id = user.getId();
            model.addAttribute("posts", user.getPosts());
            model.addAttribute("user", user);

            return "redirect:../user/profile/" + id;
        }
    }

    @RequestMapping(value="new-post", method = RequestMethod.GET)
    public String post(Model model){

        model.addAttribute("title", "Create a New Post");
        return "profile/new-post";
    }

    @RequestMapping(value="new-post", method = RequestMethod.POST)
    public String processPost(Model model, @RequestParam String title, @RequestParam String body){


        model.addAttribute("title", "Create a New Post");
        return "profile/new-post";
    }

}

