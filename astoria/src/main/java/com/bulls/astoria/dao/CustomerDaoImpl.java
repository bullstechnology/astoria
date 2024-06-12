package com.bulls.astoria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.persistence.Comision;
import com.bulls.astoria.persistence.Customer;

@Repository ("customerDao")
public class CustomerDaoImpl extends AbstractDao implements CustomerDao {
 
   
    public void addCustomer(Customer customer) {
       getSession().save(customer);
    }

    public void deleteCustomer(Customer customer) {
    	getSession().delete(customer);
    }

    public void updateCustomer(Customer customer) {
    	getSession().update(customer);
    }

    public Customer getCustomerById(int id) {
        List list = getSession().createQuery("from Customer where id=?").setParameter(0, id).list();
        return (Customer)list.get(0);
    }

    public List<Customer> getCustomers() {
        List list = getSession().createQuery("from Customer").list();
        return list;
    }
    
   
 
}

