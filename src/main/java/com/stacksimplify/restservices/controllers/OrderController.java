package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value="/users")
public class OrderController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@GetMapping(value="/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException{
		
		
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found.");
		}
	
		return userOptional.get().getOrders();		
	}
	
	
	
	@PostMapping("{userid}/orders")
	public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException {
		
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found.");
		}
		
		User user = userOptional.get();
		order.setUser(user);
		
		return orderRepository.save(order);
		
	}
	
	
	
	
	
	
	@GetMapping(value="{userid}/orders/{orderid}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderid, @PathVariable Long userid) throws UserNotFoundException {
		
		Optional<User> userOptional = userRepository.findById(userid);
		Optional<Order> orderOptional = orderRepository.findById(orderid);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found.");
		}
		else if(!orderOptional.isPresent()) {
			//Should use a different validation, but order not found exception is not 
			throw new UserNotFoundException("Order not found");
		}
		else {
			return new ResponseEntity<Order>(orderRepository.findById(orderid).get(), HttpStatus.I_AM_A_TEAPOT);
		}
	
	}
	
	
	
	
	
	
	
	

}
