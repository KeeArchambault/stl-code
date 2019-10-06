package com.kee.stlcodecafe.controllers;


import com.kee.stlcodecafe.models.Comment;
import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.Session;
import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.CommentDao;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.SessionDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value="")
public class PostController {

    @Autowired
    private SessionDao sessionDao;

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
    public String processNewPost(Model model, @ModelAttribute @Valid Post post, Errors errors) {


        model.addAttribute("title", "Create a New Post");

        if (errors.hasErrors()) {
            return "post/new-post";
        }

        for (User existingUser : userDao.findAll()) {
            for (Session testSession : sessionDao.findAll()) {
                if (existingUser.getId() == testSession.userId()) {

                    int id = testSession.userId();

                    User user = userDao.findById(id).get();
                    post.setUser(user);
                    postDao.save(post);
                    user.addPost(post);
                    userDao.save(user);
                    return "redirect:/profile";
                }
            }
        }
        return "post/new-post";
    }

    @RequestMapping(value="forum", method= RequestMethod.GET)
    public String forum(Model model) {

        model.addAttribute("title", "Forum");

        if(sessionDao.count() == 1) {
            model.addAttribute("posts", postDao.findAll());
            return "post/forum";
        }


        return "redirect:/login";
    }

    @RequestMapping(value="delete-post/{id}")
    public String remove(Model model, @PathVariable int id){

        postDao.deleteById(id);

    return "redirect:/forum";
    }

    @RequestMapping(value="add-comment/{id}")
    public String addComment(Model model, @ModelAttribute @Valid Comment comment, @PathVariable int id) {

//TODO fix comment functionality
        commentDao.save(comment);

        for (Post post : postDao.findAll()) {
            if (post.getId() == id) {
                post.addComment(comment);
                postDao.save(post);
                return "redirect:/forum";
            }
        }

        return "redirect:";
    }
}
