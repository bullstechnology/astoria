package com.bulls.astoria.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.RoleDao;
import com.bulls.astoria.persistence.Role;

@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService, Serializable {
   
    @Autowired
    private RoleDao roleDAO;

    public Role getRole(int id) {
        return roleDAO.getRole(id);
    }
    public Role getRoleXId(int id){
    	return roleDAO.getRoleXId(id);
    }
    
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

}
