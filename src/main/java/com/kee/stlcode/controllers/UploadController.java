package com.kee.stlcode.controllers;


import com.kee.stlcode.models.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping(value="")
public class UploadController extends AbstractController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
    //Save the uploaded file to this folder

        @RequestMapping(value="/upload/{id}", method=RequestMethod.GET)
        public String upload(Model model, @PathVariable int id) {

            model.addAttribute("title", "Upload a Photo");
            model.addAttribute("id", id);
            return "upload/upload";
        }
    @RequestMapping(value="/upload/{id}") // //new annotation since 4.3
    public String singleFileUpload(Model model, @RequestParam MultipartFile file, @PathVariable int id) {

        if (file.isEmpty()) {

            return "redirect:uploadStatus";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory +  file.getOriginalFilename());
            Files.write(path, bytes);

            User user = userDao.findById(id).get();
            user.setProfilePic(file, path);
            userDao.save(user);

        } catch (IOException e) {
            e.printStackTrace();
        }

            model.addAttribute("message", "Successfully Uploaded Photo.");
            return "upload/upload-status.html";
        }

}
