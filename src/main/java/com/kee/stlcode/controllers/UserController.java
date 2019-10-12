package com.kee.stlcode.controllers;

import com.kee.stlcode.models.User;
import com.kee.stlcode.models.data.PostDao;
import com.kee.stlcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value="")
public class UserController extends AbstractController {

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

        model.addAttribute("title", "Log In");

        for (User user : userDao.findAll()) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                setUserInSession(request.getSession(), user);
                model.addAttribute("user", user);
                return "redirect:/profile";
            }
        }
        model.addAttribute("errors", "Invalid login credentials.");
        return "user/login";
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

            model.addAttribute("posts", user.getPosts());
            model.addAttribute("user", user);
            model.addAttribute("title", "Posts by Me");
            return "user/profile";
        }

    }

    @RequestMapping(value = "profile/{id}", method = RequestMethod.GET)
    public String profile(HttpServletRequest request, Model model, @PathVariable int id) {

        User user = userDao.findById(id).get();

        model.addAttribute("user", user);
        model.addAttribute("title", user.getName());
        return "user/other-user-profile";

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
        }
        for(User existingUser : userDao.findAll()){
            if (user.getName().equals(existingUser.getName())) {

                model.addAttribute("usernameError", "Account with that username already exists.");
                return "user/sign-up";

            }
        }
        for(User existingUser : userDao.findAll()){
            if (user.getEmail().equals(existingUser.getEmail())) {

                model.addAttribute("emailError", "Account with that email already exists.");
                return "user/sign-up";

            }
        }

        if (!user.getPassword().equals(user.getVerify())) {

            model.addAttribute("passwordError", "Passwords do not match.");
            return "user/sign-up";
        }

        userDao.save(user);
        setUserInSession(request.getSession(), user);
        model.addAttribute("posts", user.getPosts());
        model.addAttribute("user", user);

        return "redirect:/profile";
    }

}


