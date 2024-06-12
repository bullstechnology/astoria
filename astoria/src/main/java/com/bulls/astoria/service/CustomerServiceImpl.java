package com.bulls.astoria.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.CustomerDao;
import com.bulls.astoria.persistence.Customer;


@Service("CustomerService")
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService,Serializable {

    @Autowired
    private CustomerDao customerDAO;

    /*** Annotation of applying method level Spring Security ***/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = false)
    public void addCustomer(Customer customer) {
        getCustomerDAO().addCustomer(customer);
    }

    @Transactional(readOnly = false)
    public void deleteCustomer(Customer customer) {
        getCustomerDAO().deleteCustomer(customer);
    }

    @Transactional(readOnly = false)
    public void updateCustomer(Customer customer) {
        getCustomerDAO().updateCustomer(customer);
    }

    public Customer getCustomerById(int id) {
        return getCustomerDAO().getCustomerById(id);
    }

    public List<Customer> getCustomers() {
        return getCustomerDAO().getCustomers();
    }

    public CustomerDao getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDao customerDAO) {
        this.customerDAO = customerDAO;
    }
 
}

