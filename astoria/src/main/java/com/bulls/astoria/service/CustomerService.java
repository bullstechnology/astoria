package com.bulls.astoria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulls.astoria.dao.CustomerDao;
import com.bulls.astoria.persistence.Customer;



public interface CustomerService {

    public void addCustomer(Customer customer);

    public void deleteCustomer(Customer customer);

    public void updateCustomer(Customer customer);

    public Customer getCustomerById(int id);
    public List<Customer> getCustomers();

    public CustomerDao getCustomerDAO();

    public void setCustomerDAO(CustomerDao customerDAO);
 
}

