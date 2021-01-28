package com.stacksimplify.restservices.Hello;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	
	
	//Simple method
	@RequestMapping(method = RequestMethod.GET, path = "/helloworld")
	public String helloWorld() {
		return "Hello World";
	}
	
	
	@GetMapping("/helloworld-bean")
	public UserDetails helloWorldBean() {
		return new UserDetails("Kalyan", "Reddy", "Hyderabad");
	}
	
	
	@GetMapping("/hello-int")
	public String getMessagesInI18Format(@RequestHeader(name="Accept-Language", required=false) String locale) {
		return messageSource.getMessage("label.hello", null, new Locale(locale));
	}
	
	
	@GetMapping("/hello-int2")
	public String getMessagesInI18Format2() {
		return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
	}
	
	
	
	
}
