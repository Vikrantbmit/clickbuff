package com.clickbuff.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clickbuff.constants.CBConstants;
import com.clickbuff.model.UserDetail;
import com.clickbuff.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@RequestMapping(method = RequestMethod.GET, produces = CBConstants.CONTENT_TYPE)
	public 	@ResponseBody List<UserDetail> getAllUsers(){
		
		LOGGER.info("Calling UserService getAllUsers()");
		
		List<UserDetail> listofUsers=userService.getAllUsers();
		
		LOGGER.info("Returing List of all user of size : "+listofUsers.size());
				
		return listofUsers;
		
	}
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public UserDetail getUser(@PathVariable("userId")Integer userId){
		
		LOGGER.info("Calling UserService getUserByUserName() with : "+userId);
		
		UserDetail userDetail=userService.getUserById(userId);
		
		LOGGER.info("Returing a sinigleuser : "+userDetail.toString());
				
		return userDetail;
	}
	
	@RequestMapping(value="/{userId}",method=RequestMethod.DELETE)
	@ResponseBody
	public void removeUserByUserName(@PathVariable("userId") Integer userId){
		
		LOGGER.info("Calling UserService removeUserByUserName(userName) with userName : "+userId);
		
		userService.removeUserById(userId);
		
		LOGGER.info("User Removed Successfully");
	}
	
	@RequestMapping(method=RequestMethod.POST ,consumes=CBConstants.CONTENT_TYPE,produces=CBConstants.CONTENT_TYPE)
	@ResponseBody
	public UserDetail addUser(@RequestBody UserDetail userDetail){
		
		LOGGER.info("creating new user in database userDetail : "+userDetail.toString());
		
		userDetail=userService.addUser(userDetail);
		
		LOGGER.info("User created successfully UserDetails : "+userDetail.toString());
		
		return userDetail;
	}
	
	@RequestMapping(value="/{userId}", method=RequestMethod.PUT,produces=CBConstants.CONTENT_TYPE,consumes=CBConstants.CONTENT_TYPE)
	@ResponseBody
	public UserDetail updateUser(@RequestBody UserDetail userDetail){
		
		LOGGER.info("updating new user in database userDetail : "+userDetail.toString());
		
		userDetail=userService.updateUser(userDetail);
		
		LOGGER.info("User updated successfully UserDetails : "+userDetail.toString());
		
		return userDetail;
	}
	
}
