package com.bulls.astoria.service;

import java.util.List;

import com.bulls.astoria.persistence.Role;


public interface RoleService {
 
    public Role getRole(int id);
    public Role getRoleXId(int id);
    public List<Role> getRoles();

}
