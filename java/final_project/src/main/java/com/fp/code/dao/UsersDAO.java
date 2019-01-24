package com.fp.code.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fp.code.exceptions.NotFoundException;
import com.fp.code.model.Users;
import com.fp.code.repository.UsersRepository;

@Component
public class UsersDAO {

	@Autowired
	UsersRepository usersRepository;

	public void saveUser(Users user) {
		usersRepository.save(user);
	}

	public Users findUserByUserId(long user_id) {
		return usersRepository.findByUserId(user_id);
//		return usersRepository.findByUserId(user_id).orElseThrow(() -> new NotFoundException("User not found"));
	}
	
	public Users findUserByProjectId(long project_id) {
		return usersRepository.findByProjectId(project_id);
//		return usersRepository.findByProjectId(project_id).orElseThrow(() -> new NotFoundException("User not found"));
	}

	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}

	public void assignProject(long projectId, long userId) {
		usersRepository.assignProject(projectId, userId);
	}
	
	public void deleteProject(long userId) {
		Users user = findUserByUserId(userId);
		user.setStatus("inactive");
	}
	
	public void updateUser(Users user) {
		usersRepository.save(user);
	}
	
	public void assignTaskManager(long userId, long taskId) {
		usersRepository.assignTaskManager(userId, taskId);
	}
}
