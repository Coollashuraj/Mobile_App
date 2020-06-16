package com.appdev.app.ws.ui.controller;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdev.app.ws.service.UserService;
import com.appdev.app.ws.shared.dto.UserDto;
import com.appdev.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdev.app.ws.ui.model.response.OperationStatusModel;
import com.appdev.app.ws.ui.model.response.RequestOperationStatus;
import com.appdev.app.ws.ui.model.response.UserRest;





@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping (path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
				 MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		
		UserRest returnValue = new UserRest();
		
		UserDto userDto = userService.getUserByUserId(id);
		
		
		BeanUtils.copyProperties(userDto, returnValue);

		 ModelMapper modelMapper = new ModelMapper();
		 returnValue = modelMapper.map(userDto, UserRest.class);

		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest CreateUser(@RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();

		//UserDto userDto = new UserDto();
		//BeanUtils.copyProperties(userDetails, userDto);
		 ModelMapper modelMapper = new ModelMapper();
		 UserDto userDto=modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.CreateUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);

		// returnValue =//modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails) {
		
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		

		UserDto updatedUser = userService.UpdateUser(id,userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
		
	}
	
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnVal= new OperationStatusModel();
		
		userService.deleteUser(id);
		returnVal.setOperationName(RequestOperationName.DELETE.name());
		returnVal.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnVal;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "2") int limit) {
		List<UserRest> returnValue = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit);
		
		

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;
	}
	/*
	 * @PostMapping//(consumes = { MediaType.APPLICATION_XML_VALUE,
	 * MediaType.APPLICATION_JSON_VALUE }, produces = {
	 * //MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) public
	 * String createUser(@RequestBody UserDetailsRequestModel userDetails) throws
	 * Exception { UserRest returnValue = new UserRest();
	 * 
	 * // UserDto userDto = new UserDto(); // BeanUtils.copyProperties(userDetails,
	 * userDto); ModelMapper modelMapper = new ModelMapper(); UserDto userDto =
	 * modelMapper.map(userDetails, UserDto.class);
	 * 
	 * UserDto createdUser = userService.createUser(userDto); returnValue =
	 * modelMapper.map(createdUser, UserRest.class);
	 * 
	 * return returnValue; }
	 * 
	 * @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
	 * MediaType.APPLICATION_JSON_VALUE }, produces = {
	 * MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ApiImplicitParams({
	 * 
	 * @ApiImplicitParam(name="authorization",
	 * value="${userController.authorizationHeader.description}",
	 * paramType="header") }) public UserRest updateUser(@PathVariable String
	 * id, @RequestBody UserDetailsRequestModel userDetails) { UserRest returnValue
	 * = new UserRest();
	 * 
	 * UserDto userDto = new UserDto(); userDto = new ModelMapper().map(userDetails,
	 * UserDto.class);
	 * 
	 * UserDto updateUser = userService.updateUser(id, userDto); returnValue = new
	 * ModelMapper().map(updateUser, UserRest.class);
	 * 
	 * return returnValue; }
	 */
}
