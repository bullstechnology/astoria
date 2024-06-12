package com.bulls.astoria.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.persistence.Dominio;
import com.bulls.astoria.persistence.Role;

@Repository ("roleDao")
public class RoleDaoImpl extends AbstractDao  implements RoleDao {
   

   
    public Role getRole(int id) {
        Role role = (Role) getSession().load(Role.class, id);
        return role;
    }
    
    public Role getRoleXId(int id) {
    	Query query = getSession().createQuery("from Role where id =:id");
    	query.setInteger("id",id);
        return (Role)query.uniqueResult();
    }
    
    public List<Role> getRoles() {
    	
    	Query query = getSession().createQuery("from Role rol order by rol.role");
    	return (List <Role>) query.list();
    }

}

