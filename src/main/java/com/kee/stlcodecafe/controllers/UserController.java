package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.Session;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.SessionDao;
import com.kee.stlcodecafe.models.data.UserDao;
import com.kee.stlcodecafe.models.forms.AddPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(value="")
public class UserController {

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute("title", "Log In");
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String loginName, @RequestParam String loginPassword) {

//TODO fix login issue, multiple users can register but only the first can sign in again

        model.addAttribute("title", "Log In");

        for (User user : userDao.findAll()) {
            if (user.getName().equals(loginName) && user.getPassword().equals(loginPassword)) {
                Session session = new Session(user);
                sessionDao.save(session);
                return "redirect:/profile"; //redirects to the appropriate user profile

            } else {
                model.addAttribute("errors", "Invalid login credentials.");
                return "user/login";
            }
        }

        model.addAttribute("errors", "");
        return "user/login";
    }


    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(Model model) {
        sessionDao.deleteAll();

        return "redirect:/login";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(Model model) {

        model.addAttribute("title", "Profile");

        for (User existingUser : userDao.findAll()) {
            for (Session testSession : sessionDao.findAll()) {
                if (existingUser.getId() == testSession.userId()) {

                    model.addAttribute("user", existingUser);// passes the user id to the template to display the correct posts
                    return "user/profile";
                }
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.GET)
    public String signUp(Model model, User user) {

        model.addAttribute("title", "Sign Up");

        model.addAttribute("user", user);

        return "user/sign-up";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public String processSignUp(Model model, @ModelAttribute @Valid User user, Errors errors) {

        if (errors.hasErrors() || !user.getPassword().equals(user.getVerify())) {

            return "user/sign-up";

        } else {
            userDao.save(user);
            Session session = new Session(user);
            sessionDao.save(session);
            model.addAttribute("posts", user.getPosts());
            model.addAttribute("user", user);

            return "redirect:/profile";
        }
    }

}



