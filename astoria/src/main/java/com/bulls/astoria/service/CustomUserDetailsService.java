package com.bulls.astoria.service;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.UserDao;
import com.bulls.astoria.persistence.User;

@Service
@Transactional(readOnly=true)
public class CustomUserDetailsService implements UserDetailsService,Serializable {
   
    @Autowired
    private UserDao userDAO;   

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
       
        User domainUser = userDAO.getUser(login);
       
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        /*return new org.springframework.security.core.userdetails.User(
            domainUser.getLogin(),
            domainUser.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(domainUser.getRole().getId())
        );*/
        
        return new org.springframework.security.core.userdetails.User(
                domainUser.getLogin(),
                domainUser.getPassword(),
                domainUser.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(domainUser.getRole().getId())
            );
        
      
    }
   
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
   
    public List<String> getRoles(Integer role) {

        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_GE");
        }else if (role.intValue() == 3) {
        	roles.add("ROLE_PL");
        } else if (role.intValue() == 4) {
        	roles.add("ROLE_CL");
        } else if (role.intValue() == 5) {
        	roles.add("ROLE_AG");
        }else if (role.intValue() == 6) {
        	roles.add("ROLE_VE");
        }else if (role.intValue() == 7) {
        	roles.add("ROLE_CO");
        }else if (role.intValue() == 8) {
        	roles.add("ROLE_FA");
        }else if (role.intValue() == 9) {
        	roles.add("ROLE_HN");
        }
        return roles;
    }
   
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
       
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

} 
