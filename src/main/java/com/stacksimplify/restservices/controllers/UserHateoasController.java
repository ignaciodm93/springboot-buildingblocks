package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value="/hateoas/users")
public class UserHateoasController {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id){
		
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			Long userid = user.getUserid();
			Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selflink);
			EntityModel<User> finalResource = EntityModel.of(user);
			return finalResource;
		}
		catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	
	
	
	@GetMapping
	public CollectionModel<User> getAllUsers(){
		
		List<User> allUsers = userService.getAllUsers();
		
		//for every user, creates a self link
		for(User user : allUsers) {
			Long userid = user.getUserid();
			
			Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selfLink);
			
			
			//relationship link with getAllOrders
			try {
				CollectionModel<Order> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class)
						.getAllOrders(userid);
				
				Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
				
				user.add(ordersLink);
				
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		//Self link for getAllUsers
		Link selfLinkGetAllUsers = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel(); 
				
		CollectionModel<User> finalResources = CollectionModel.of(allUsers, selfLinkGetAllUsers);
		return finalResources;
		
	}
	
	
}
