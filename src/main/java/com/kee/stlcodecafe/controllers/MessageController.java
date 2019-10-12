package com.kee.stlcodecafe.controllers;

import com.kee.stlcodecafe.models.Message;
import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "")
public class MessageController extends AbstractController {

    @RequestMapping(value = "send-message/{id}", method = RequestMethod.GET)
    public String displayMessageForm(HttpServletRequest request, Model model, Message message, @PathVariable int id){

        int senderId = getUserFromSession(request.getSession()).getId();

        model.addAttribute("recipientId", id);
        model.addAttribute("senderId", senderId);
        model.addAttribute("title", "Send a Message to " + userDao.findById(id).get().getName());

        return "message/message-form";
    }

    @RequestMapping(value = "send-message/{recipientId}/{senderId}")
    public String processMessageForm(Model model, @ModelAttribute @Valid Message message, @PathVariable int recipientId, @PathVariable int senderId){

        User recipient = userDao.findById(recipientId).get();
        User sender = userDao.findById(senderId).get();

        message.setRecipient(recipient);
        message.setSender(sender);
        messageDao.save(message);

//        message.setSender_recipient(sender, recipient);
//
//        recipient.addToReceivedMessages(message);
//        userDao.save(recipient);
//
//        sender.addToSentMessages(message);
//        userDao.save(sender);

        return "redirect:/profile/" + recipientId;

    }

//    TODO add route display received messages

    @RequestMapping("inbox")
    public String inbox(HttpServletRequest request, Model model){

        model.addAttribute("title", "Inbox");

        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:/login";
        }

        List<Message> messages = new ArrayList<Message>() {
        };
        User sessionUser = getUserFromSession(request.getSession());
        int userId = sessionUser.getId();

        for(Message message : messageDao.findAll()){
            if(userId == message.getRecipient().getId()) {
                messages.add(message);
            }
        }

        Collections.reverse(messages);

        model.addAttribute("messages", messages);
        model.addAttribute("userId", userId);

        return "message/inbox";

    }

    @RequestMapping(value="sent")
    public String sent(HttpServletRequest request, Model model) {

        model.addAttribute("title", "Sent Messages");

        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:/login";
        }
        List<Message> messages = new ArrayList<Message>() {
        };
        User sessionUser = getUserFromSession(request.getSession());
        int userId = sessionUser.getId();

        for(Message message : messageDao.findAll()) {
            if (userId == message.getSender().getId()) {
                messages.add(message);
            }
        }
        Collections.reverse(messages);

        model.addAttribute("messages", messages);

        return "message/sent";
    }

    @RequestMapping(value="received-message/{id}")
    public String viewReceivedMessage(Model model, @PathVariable int id){

        Message message = messageDao.findById(id).get();

        model.addAttribute("message", message);

        return "message/received-message";
    }

    @RequestMapping(value="sent-message/{id}")
    public String viewSentMessage(Model model, @PathVariable int id){

        Message message = messageDao.findById(id).get();

        model.addAttribute("message", message);

        return "message/sent-message";
    }

    @RequestMapping(value="delete-message/{id}")
    public String remove(Model model, @PathVariable int id){

        messageDao.deleteById(id);

        return "redirect:/inbox";
    }


}
