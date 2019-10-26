package com.kee.stlcode.controllers;

import com.kee.stlcode.models.Post;
import com.kee.stlcode.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "")
public class SearchController extends AbstractController{

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchResults(HttpServletRequest request, Model model) {

        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Search All Posts");
        return "search/search-form";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchResults(HttpServletRequest request, Model model, @RequestParam String searchTerm){

        if(getUserFromSession(request.getSession()) == null) {

            return "redirect:/login";
        }
        model.addAttribute("title", "Search Results");

        List<Post> postResults = new ArrayList<Post>();

        for (Post post : postDao.findAll()){
            if (post.getTitle().toLowerCase().contains(searchTerm.toLowerCase())
                    || post.getBody().toLowerCase().contains(searchTerm.toLowerCase())){
                postResults.add(post);
            }
        }

        List<User> userResults = new ArrayList<User>();
        for (User user : userDao.findAll()){
            if(user.getName().toLowerCase().equals(searchTerm.toLowerCase())){
                userResults.add(user);
                File profilePic = user.getProfilePic();

                if(profilePic == null){
                    String fileName = "default.png";
                    int defaultId = 1;
                    model.addAttribute("id", defaultId);
                    model.addAttribute("profilePic", fileName);
                }else {
                    Path filePath = Paths.get(profilePic.getPath());
                    String fileName = profilePic.getName();
                    model.addAttribute("profilePic", fileName);
                }
            }
        }
        model.addAttribute("userResults", userResults);
        model.addAttribute("postResults", postResults);
        return "search/search-results";
    }

}
