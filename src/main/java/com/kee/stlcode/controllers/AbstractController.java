package com.kee.stlcode.controllers;

import javax.servlet.http.HttpSession;


import com.kee.stlcode.models.User;
import com.kee.stlcode.models.data.MessageDao;
import com.kee.stlcode.models.data.PostDao;
import com.kee.stlcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

        @Autowired
        protected UserDao userDao;

        @Autowired
        protected PostDao postDao;

        @Autowired
        protected MessageDao messageDao;

        public static final String userSessionKey = "id";


        protected User getUserFromSession(HttpSession session) {

            Integer id = (Integer) session.getAttribute(userSessionKey);
            return id == null ? null : userDao.findById(id).get();
        }

        protected void setUserInSession(HttpSession session, User user) {
            session.setAttribute(userSessionKey, user.getId());
        }

}
