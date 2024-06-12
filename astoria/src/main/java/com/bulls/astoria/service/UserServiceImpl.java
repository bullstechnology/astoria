package com.bulls.astoria.service;



import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.UserDao;
import com.bulls.astoria.persistence.User;

@Service ("UserService")
@Transactional
public class UserServiceImpl implements UserService, Serializable {
   
    @Autowired
    private UserDao userDAO;

    public User getUser(String login) {
        return userDAO.getUser(login);
    }
    
    public void editarUsuario(User usuario){
    	userDAO.editarUser(usuario);
    	return ;
    }

}
