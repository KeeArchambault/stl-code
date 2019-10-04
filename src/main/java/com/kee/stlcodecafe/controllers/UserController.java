package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.Session;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.SessionDao;
import com.kee.stlcodecafe.models.data.UserDao;
import com.kee.stlcodecafe.models.forms.AddPostForm;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String processLogin(Model model, @RequestParam String name, @RequestParam String password) {

        model.addAttribute("title", "Log In");

        for (User user : userDao.findAll()) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                int id = user.getId(); //gets the user's id to add to the session
                Session session = new Session(id);
                sessionDao.save(session);

                model.addAttribute("id", id);
                return "redirect:/profile/"; //redirects to the appropriate user profile
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
                if (existingUser.getId() == testSession.getSessionKey()) {

                    model.addAttribute("id", existingUser.getId());// passes the user id to the template to display the correct posts
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
            int id = user.getId();
            model.addAttribute("posts", user.getPosts());
            model.addAttribute("user", user);

            return "redirect:../user/profile/" + id;
        }
    }

    @RequestMapping(value = "new-post", method = RequestMethod.GET)
    public String newPost(Model model, Post post, @RequestParam int id) {

        model.addAttribute("title", "Create a New Post");

        if (sessionDao.count() == 1) {
            User user = userDao.findById(id).get();
            Iterable<Post> posts = postDao.findAll();
            AddPostForm form = new AddPostForm(user, posts);

            model.addAttribute("id", id);
            model.addAttribute("form", form);
            model.addAttribute("posts", posts);
            model.addAttribute("user", user);
            return "user/new-post";
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.POST)
    public String processNewPost(Model model, @RequestParam int postId, @RequestParam int id, Errors errors) {


        model.addAttribute("title", "Create a New Post");

        if (errors.hasErrors()) {
            return "user/new-post";
        }

        Post post =  postDao.findById(postId).get();
        User user = userDao.findById(id).get();
        List<Post> posts = user.getPosts();
        model.addAttribute("user", user);


        user.addPost(post);

        userDao.save(user);
        return "redirect:";
    }

    @RequestMapping(value="forum", method= RequestMethod.GET)
    public String forum(Model model) {

        model.addAttribute("title", "Forum");

        if(sessionDao.count() == 1) {
           model.addAttribute("posts", postDao.findAll());
           return "user/forum";
        }


        return "redirect:/login";
    }
}



