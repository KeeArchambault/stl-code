package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.Session;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.SessionDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="user")
public class UserController {

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String login(Model model){

        model.addAttribute("title", "Log In");
        return "user/login";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String name) {

        for (User user : userDao.findAll()) {
            if (user.getName().equals(name)) { //checks user data for a matching name
                int id = user.getId(); //gets the user's id to add to the session
                Session session = new Session(id);
                sessionDao.save(session);

                model.addAttribute("id",id);
                model.addAttribute("title", "Log In");
                return "redirect:user/profile/" + id; //redirects to the appropriate user profile
            }

        }
            model.addAttribute("title", "Log In");
            return "user/login";
    }

    @RequestMapping(value="logout", method = RequestMethod.GET)
    public String logout(Model model){
        sessionDao.deleteAll();

        return "redirect:user/login";
    }

    @RequestMapping(value = "profile/{id}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable int id){

        model.addAttribute("title", "Profile");

        for(User existingUser : userDao.findAll()) {
            for (Session testSession : sessionDao.findAll()) {
                if (existingUser.getId() == testSession.getSessionKey()) {

                    model.addAttribute("id", existingUser.getId());// passes the user id to the template to display the correct posts
                    return "user/profile";
                }
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value="sign-up", method = RequestMethod.GET)
    public String signUp(Model model, User user){

        model.addAttribute("title", "Sign Up");

        model.addAttribute("user", user);

        return "user/sign-up";
    }

    @RequestMapping(value="sign-up", method= RequestMethod.POST)
    public String processSignUp(Model model, @ModelAttribute @Valid User user, Errors errors) {

        if (errors.hasErrors() || !user.getPassword().equals(user.getVerify())) {

            return "user/sign-up";

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

//        if(!session.isEmpty()) {
//            model.addAttribute("session", session); // passes the user id to the template
//            return "user/new-post";
//        }

        return "redirect:/login";
    }

    @RequestMapping(value="new-post", method = RequestMethod.POST)
    public String processPost(Model model, @RequestParam String title, @RequestParam String body){


        model.addAttribute("title", "Create a New Post");
        return "user/new-post";
    }

    @RequestMapping(value="forum", method= RequestMethod.GET)
    public String forum(Model model) {

        model.addAttribute("title", "Forum");

//        if(!session.isEmpty()) {
//            model.addAttribute("session", session); // passes the user id to the template
//            model.addAttribute("posts", postDao.findAll());
//            return "user/forum";
//        }


        return "redirect:/login";
    }
}



