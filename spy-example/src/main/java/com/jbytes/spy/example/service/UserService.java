package com.jbytes.spy.example.service;

import com.jbytes.spy.example.model.User;

/**
 * @author wongoo
 */
public class UserService {

    public User getUser(String name) {
        User u = new User();
        u.setName(name);
        u.setAge(18);
        return u;
    }
}
