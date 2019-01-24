package com.fp.code.controller;

import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fp.code.model.Project;
import com.fp.code.model.Users;
import com.fp.code.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UsersService usersService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Users user) {
		usersService.saveUser(user);
	}

	@GetMapping("/{id}")
	public Users getByUserId(@PathVariable("id") Long id) {
		return usersService.findUserByUserId(id);
	}

	@GetMapping("/project/{id}")
	public Users getByProjectId(@PathVariable("id") long id) {
		return usersService.findUserByProjectId(id);
	}

//	@Produces("MediaType.APPLICATION_JSON")
//	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Users> list() {
		return usersService.getAllUsers();
	}

	@PutMapping("add-project-manager/{projectId}/{userId}")
	public void assignProject(@PathVariable("projectId") long projectId, @PathVariable("userId") long userId) {
		usersService.assignProject(projectId, userId);
	}
	
	@PutMapping("/remove-user/{userId}")
	public void deleteProject(@PathVariable("userId") long userId) {
		usersService.deleteProject(userId);
	}
	
	@PutMapping("/update-user") 
	public void updateUser(@RequestBody Users user) {
		usersService.updateUser(user);
	}
	
	@PutMapping("/assign-task-manager/{userId}/{taskId}") 
	public void assignTaskManager(@PathVariable("userId") long userId, @PathVariable("taskId") long taskId) {
		usersService.assignTaskManager(userId, taskId);
	}
}
