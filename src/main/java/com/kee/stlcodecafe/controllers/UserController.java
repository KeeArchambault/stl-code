package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value="")
public class UserController extends AbstractController{

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
    public String processLogin(HttpServletRequest request, Model model, @RequestParam String username, @RequestParam String password) {

//TODO fix login issue, multiple users can register but only the first can sign in again

        model.addAttribute("title", "Log In");

        for (User user : userDao.findAll()) {
            if (user.getName().equals(username)) {
                if (user.getPassword().equals(password)) {
                    setUserInSession(request.getSession(), user);
                    model.addAttribute("user", user);

//TODO implement login

                } else {
                    model.addAttribute("errors", "Invalid login credentials.");
                    return "user/login";
                }
            }

        }
        return "redirect:/profile";
    }


    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/login";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest request, Model model) {

        if(getUserFromSession(request.getSession()) == null){
            return "redirect:/login";

        }else {

            User user = getUserFromSession(request.getSession());
            model.addAttribute("user", user);
            model.addAttribute("title", "Profile");

            return "user/profile";
        }


    }

    @RequestMapping(value = "sign-up", method = RequestMethod.GET)
    public String signUp(Model model, User user) {

        model.addAttribute("title", "Sign Up");

        model.addAttribute("user", user);

        return "user/sign-up";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public String processSignUp(HttpServletRequest request, Model model, @ModelAttribute @Valid User user, Errors errors) {

        if (errors.hasErrors() || !user.getPassword().equals(user.getVerify())) {

            return "user/sign-up";

        } else {
            userDao.save(user);
            setUserInSession(request.getSession(), user);
            model.addAttribute("posts", user.getPosts());
            model.addAttribute("user", user);

            return "redirect:/profile";
        }
    }

}



