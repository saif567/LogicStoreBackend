package com.project.ecommerce.dto;

import java.util.Set;

import com.project.ecommerce.entity.Address;
import com.project.ecommerce.entity.Customer;
import com.project.ecommerce.entity.Order;
import com.project.ecommerce.entity.OrderItem;

import lombok.Data;

@Data
public class Purchase {
	
	
	private Customer customer;
	
	private Address shippingAddress;
	
	private Address billingAddress;
	
	private Order order;
	
	private Set<OrderItem> orderItems;
	

}
