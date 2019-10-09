package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Message;
import com.kee.stlcodecafe.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "")
public class MessageController extends AbstractController {

    @RequestMapping(value = "message/{id}")
    public String displayMessageForm(HttpServletRequest request, Model model, Message message, @PathVariable int id){

        User recipient = userDao.findById(id).get();

        User sender = getUserFromSession(request.getSession());

        model.addAttribute("recipient", recipient);
        model.addAttribute("sender", sender);

        return "message/message-form";
    }

//    TODO add route to process new messages

//    TODO add route display received messages

//    TODO add route to display sent messages


}
