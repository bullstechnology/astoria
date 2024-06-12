package com.bulls.astoria.dao;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.persistence.User;


@Repository ("UserDao")
public class UserDaoImpl extends AbstractDao implements UserDao {
   
   public User getUser(String login) {
        List<User> userList = new ArrayList<User>();
        Query query = getSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", login);
        userList = query.list();
        if (userList.size() > 0)
            return userList.get(0);
        else
            return null;   
    }
   
   @Transactional
   public void saveUser(User user){
	   getSession().clear();
	   User usermerge = (User)getSession().merge(user);
   }
   
   @Transactional
   public void editarUser(User user){
	   getSession().clear();
	   User usermerge = (User)getSession().merge(user);
	   getSession().flush();
   }
   

}

