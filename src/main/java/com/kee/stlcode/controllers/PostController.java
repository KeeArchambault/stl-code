package com.kee.stlcode.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.kee.stlcode.models.Comment;
import com.kee.stlcode.models.Post;
import com.kee.stlcode.models.User;
import com.kee.stlcode.models.data.CommentDao;
import com.kee.stlcode.models.data.PostDao;
import com.kee.stlcode.models.data.UserDao;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

        List<Comment> comments = new ArrayList<>(post.getComments());
        Collections.sort(comments);
        Collections.reverse(comments);

        model.addAttribute("comments", comments);
        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        model.addAttribute("title", post.getTitle());

        return "post/post";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.GET)
    public String newPost(HttpServletRequest request, Model model, Post post) {

        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Create a New Post");

        model.addAttribute("post", post);
        return "post/new-post";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.POST)
    public String processNewPost(HttpServletRequest request, Model model, @ModelAttribute @Valid Post post, Errors errors) {

        model.addAttribute("title", "New Post");

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

        model.addAttribute("title", "All Posts");

        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:/login";
        }
        List<Post> posts = new ArrayList<Post>();
        for(Post post :postDao.findAll()) {
            if(!post.isDeleted()){
                posts.add(post);
            }
        }

        Collections.sort(posts);
        Collections.reverse(posts);


        model.addAttribute("posts", posts);
        return "post/forum";


    }

    @RequestMapping(value="delete-post/{id}")
    public String remove(Model model, @PathVariable int id){

            Post post = postDao.findById(id).get();
            post.setDeleted(true);
            postDao.save(post);
    return "redirect:/profile";
    }


    @RequestMapping(value="add-comment/{id}", method = RequestMethod.POST)
    public String addComment(HttpServletRequest request, Model model, @ModelAttribute @Valid Comment comment, Errors errors, @PathVariable int id) {

        if (errors.hasErrors()) {

            return "redirect:";

        }else {

            User user = getUserFromSession(request.getSession());

            comment.setUser(user);
            commentDao.save(comment);

            user.addComment(comment);
            userDao.save(user);


            for (Post post : postDao.findAll()) {
                if (post.getId() == id) {

                    comment.setPost(post);
                    commentDao.save(comment);

                    post.addComment(comment);
                    postDao.save(post);
                    return "redirect:/post/" + id;
                }
            }
        }

        return "redirect:";
    }
}
