package com.kee.stlcodecafe.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.kee.stlcodecafe.models.Comment;
import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.CommentDao;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value="")
public class PostController extends AbstractController{

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value="post/{id}", method = RequestMethod.GET)
    public String viewPost(Model model, @PathVariable int id, Comment comment){

        Post post = postDao.findById(id).get();

        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        model.addAttribute("title", post.getTitle());

        return "post/post";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.GET)
    public String newPost(Model model, Post post) {

        model.addAttribute("title", "Create a New Post");

        model.addAttribute("post", post);
        return "post/new-post";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.POST)
    public String processNewPost(HttpServletRequest request, Model model, @ModelAttribute @Valid Post post, Errors errors) {


        model.addAttribute("title", "Create a New Post");

        if (errors.hasErrors()) {
            return "post/new-post";
        }

        User user = getUserFromSession(request.getSession());

        int id = user.getId();

        post.setUser(user);
        postDao.save(post);
        user.addPost(post);
        userDao.save(user);
        return "redirect:/profile";

    }

    @RequestMapping(value="forum", method= RequestMethod.GET)
    public String forum(HttpServletRequest request, Model model) {


        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Forum");

        model.addAttribute("posts", postDao.findAll());
        return "post/forum";


    }

    @RequestMapping(value="delete-post/{id}")
    public String remove(Model model, @PathVariable int id){

        postDao.deleteById(id);

    return "redirect:/forum";
    }


    @RequestMapping(value="add-comment/{id}", method = RequestMethod.POST)
    public String addComment(HttpServletRequest request, Model model, @ModelAttribute @Valid Comment comment, Errors errors, @PathVariable int id) {

//TODO fix comment functionality

        if (errors.hasErrors()) {

            return "redirect:";


        }else {

            User user = getUserFromSession(request.getSession());
            commentDao.save(comment);
            user.addComment(comment);
            userDao.save(user);
            String username = user.getName();

            for (Post post : postDao.findAll()) {
                if (post.getId() == id) {
                    post.addComment(comment);
                    postDao.save(post);
                    return "redirect:/forum";
                }
            }
        }

        return "redirect:";
    }
}
