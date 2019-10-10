package com.kee.stlcodecafe.controllers;

import javax.servlet.http.HttpSession;


import com.kee.stlcodecafe.models.User;
import com.kee.stlcodecafe.models.data.PostDao;
import com.kee.stlcodecafe.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

        @Autowired
        protected UserDao userDao;

        @Autowired
        protected PostDao postDao;

    //the attribute we want to be able to retrieve from the User object after we have passed it into the session with setUserInSession
    public static final String userSessionKey = "id";


    protected User getUserFromSession(HttpSession session) {

        //gets the id from the session and casts it to Integer so it can be used to query the userDao and return the correct user
        Integer id = (Integer) session.getAttribute(userSessionKey);
        //if null returns null, if not null queries UserDao and return current User in the session
        return id == null ? null : userDao.findById(id).get();
    }

    //Session and User objects are passed into method
    protected void setUserInSession(HttpSession session, User user) {
        //the session's userSessionKey attribute is set to the user's id attribute
        session.setAttribute(userSessionKey, user.getId());
    }

}
