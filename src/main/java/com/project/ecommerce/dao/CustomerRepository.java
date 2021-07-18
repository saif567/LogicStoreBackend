package com.project.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	public Customer findByEmail(String email);
	
}
