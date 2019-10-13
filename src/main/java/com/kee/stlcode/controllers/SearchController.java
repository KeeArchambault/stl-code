package com.kee.stlcode.controllers;

import com.kee.stlcode.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

        List<Post> results = new ArrayList<Post>();

        for (Post post : postDao.findAll()){
            if (post.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) || post.getBody().toLowerCase().contains(searchTerm.toLowerCase())){
                results.add(post);
            }
        }

        model.addAttribute("results", results);
        return "search/search-results";
    }


}
