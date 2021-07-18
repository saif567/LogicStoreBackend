package com.project.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dao.CustomerRepository;
import com.project.ecommerce.dto.Purchase;
import com.project.ecommerce.dto.PurchaseResponse;
import com.project.ecommerce.entity.Customer;
import com.project.ecommerce.entity.Order;
import com.project.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	
	private CustomerRepository customerRepository;
	
	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository=customerRepository;
	}
	
	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		// TODO Auto-generated method stub
		
		//retrieve the order info from dto
		
		Order order = purchase.getOrder();
		//generate tracking number
		String  orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		//populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item->order.addOrder(item));
		
		//populate order with billing address and shipping address
		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());		
		//populate customer with order
		Customer customer = purchase.getCustomer();
		Customer customerFromDB= this.customerRepository.findByEmail(customer.getEmail());
		if(customerFromDB!=null) {
			customer=customerFromDB;
		}
		customer.addOrder(order);
		
		
		//save to the database
		customerRepository.save(customer);
		
		//return a response 
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
		//generate random UUID number (UUID version-4)
		//for details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
		
		
		return UUID.randomUUID().toString();
	}

}
