package com.bulls.astoria.dao;

import java.util.List;

import com.bulls.astoria.persistence.Role;


public interface RoleDao {
 
    public Role getRole(int id);
    public Role getRoleXId(int id);
    public List<Role> getRoles();

}
