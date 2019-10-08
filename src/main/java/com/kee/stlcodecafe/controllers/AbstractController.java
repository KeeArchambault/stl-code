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

        public static final String userSessionKey = "id";

        protected User getUserFromSession(HttpSession session) {

            Integer id = (Integer) session.getAttribute(userSessionKey);
            return id == null ? null : userDao.findById(id).get();
        }

        protected void setUserInSession(HttpSession session, User user) {
            session.setAttribute(userSessionKey, user.getId());
        }



}
