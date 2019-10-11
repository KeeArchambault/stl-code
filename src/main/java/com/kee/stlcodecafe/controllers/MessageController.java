package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Message;
import com.kee.stlcodecafe.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "")
public class MessageController extends AbstractController {

    @RequestMapping(value = "message/{id}", method = RequestMethod.GET)
    public String displayMessageForm(HttpServletRequest request, Model model, Message message, @PathVariable int id){

        int senderId = getUserFromSession(request.getSession()).getId();

        model.addAttribute("recipientId", id);
        model.addAttribute("senderId", senderId);
        model.addAttribute("recipientName", userDao.findById(id).get().getName());

        return "message/message-form";
    }

    @RequestMapping(value = "message/{recipientId}/{senderId}")
    public String processMessageForm(Model model, @ModelAttribute @Valid Message message, @PathVariable int recipientId, @PathVariable int senderId){

        User recipient = userDao.findById(recipientId).get();
        User sender = userDao.findById(senderId).get();

        messageDao.save(message);

        recipient.addToReceivedMessages(message);
        userDao.save(recipient);

        sender.addToSentMessages(message);
        userDao.save(sender);

        return "redirect:/profile/" + recipientId;

    }

//    TODO add route to process new messages

//    TODO add route display received messages

//    TODO add route to display sent messages


}
