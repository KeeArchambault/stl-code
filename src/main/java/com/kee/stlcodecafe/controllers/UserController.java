package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.Session;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.SessionDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private SessionDao sessionDao;



    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayLogin(Model model) {

        model.addAttribute("title", "Log In");
        return "profile/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String username, String password) {

        for (User user : userDao.findAll()) {
            if (username.equals(user.getName())) {
                int targetId = user.getId();

                sessionDao.save(user);

            }
        }

        model.addAttribute("title", "Log In");
        return "profile/login";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.GET)
    public String signUp(Model model, User user) {

        model.addAttribute("user", user);
        model.addAttribute("title", "Sign Up");
        return "profile/sign-up";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public String processSignUp(Model model, @ModelAttribute @Valid User user, Errors errors) {

        for (User existUser : userDao.findAll()) {
            if (user.getId() != existUser.getId()) {
                if (errors.hasErrors() || !user.getPassword().equals(user.getVerify())) {
                    return "user/sign-up";
                } else {
                    int id = user.getId();
                    userDao.save(user);
                    return "redirect:/profile" + id;


                }
            } else {
                int id = user.getId();
                sessionDao.save(user);
                return "redirect:/profile" + id;
            }

        }
        return "profile/sign-up";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(Model model, @RequestParam Session session) {

        if(session.getUser() != null)

        model.addAttribute("user", session.getUser());
        model.addAttribute("title", "Profile");
        return "profile/index";
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String logout(Model model, @RequestParam Session session) {

        User user = session.getUser();
        sessionDao.delete(user);
        return "redirect: /login";
    }
}
