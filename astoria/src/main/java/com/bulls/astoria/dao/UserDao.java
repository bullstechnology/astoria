package com.bulls.astoria.dao;

import java.util.List;

import com.bulls.astoria.persistence.User;


public interface UserDao {
 
    public User getUser(String login);
    public void saveUser(User user);
    public void editarUser(User user);
    
}
