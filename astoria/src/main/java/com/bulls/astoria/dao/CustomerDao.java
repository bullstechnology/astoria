package com.bulls.astoria.dao;

import java.util.List;

import com.bulls.astoria.persistence.Customer;



public interface CustomerDao {

		void addCustomer(Customer customer) ;
	    void deleteCustomer(Customer customer);

	    void updateCustomer(Customer customer) ;

	    Customer getCustomerById(int id);
	    List<Customer> getCustomers();
	 
}
