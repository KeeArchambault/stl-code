package com.kee.stlcode.controllers;


import com.kee.stlcode.models.User;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/myImage")
public class ImageController extends AbstractController{


    @RequestMapping(value = "/imageDisplay/{id}", method = RequestMethod.GET)
    public void showImage(@PathVariable int id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {

        User user = userDao.findById(id).get();

        File file = user.getProfilePic();
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(bytesArray);

        response.getOutputStream().close();
    }
}
