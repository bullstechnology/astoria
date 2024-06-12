package com.bulls.astoria.service;

import com.bulls.astoria.persistence.User;


public interface UserService {
 
    public User getUser(String login);
    public void editarUsuario(User usuario);

}
