package com.kee.stlcode.controllers;

import com.kee.stlcode.models.Post;
import com.kee.stlcode.models.User;
import com.kee.stlcode.models.data.PostDao;
import com.kee.stlcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


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

    @RequestMapping(value = "profile", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public String profile(HttpServletRequest request, Model model) {

        if(getUserFromSession(request.getSession()) == null){
            return "redirect:/login";

        }else {

            User user = getUserFromSession(request.getSession());

            List<Post> posts = new ArrayList<Post>();
            for(Post post : user.getPosts()){
                if (!post.isDeleted()){
                    posts.add(post);
                }
            }

            Collections.sort(posts);
            Collections.reverse(posts);

            File profilePic = user.getProfilePic();

            if(profilePic == null){
                String fileName = "default.png";
                model.addAttribute("profilePic", fileName);
            }else {
                Path filePath = Paths.get(profilePic.getPath());
                String fileName = profilePic.getName();
                model.addAttribute("profilePic", fileName);
            }
                model.addAttribute("posts", posts);
                model.addAttribute("user", user);
                model.addAttribute("title", "Posts by Me");
                return "user/profile";

        }

    }

    @RequestMapping(value = "profile/{id}", method = RequestMethod.GET)
    public String otherProfile(HttpServletRequest request, Model model, @PathVariable int id) {

        int currentUserId = getUserFromSession(request.getSession()).getId();

        if(id == currentUserId){

            return "redirect:/profile";
        }

        User user = userDao.findById(id).get();

        List<Post> posts = new ArrayList<Post>();
        for(Post post : user.getPosts()){
            if (!post.isDeleted()){
                posts.add(post);
            }
        }
        Collections.sort(posts);
        Collections.reverse(posts);

        File pic = user.getProfilePic();


        if(pic == null){
            String fileName = "default.png";
            model.addAttribute("profilePic", fileName);
        }else {
            String picName = pic.getName();
            String profilePic = System.getProperty("user.dir") + "/src/main/resources/static/uploads/" + picName;
            model.addAttribute("profilePic", profilePic);
        }

        model.addAttribute("posts", posts);
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



