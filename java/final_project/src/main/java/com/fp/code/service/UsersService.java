package com.fp.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.fp.code.dao.UsersDAO;
import com.fp.code.model.Users;

@Service
@Transactional
public class UsersService {

	@Autowired
	UsersDAO usersDAO;

	public void saveUser(Users user) {
		usersDAO.saveUser(user);
	}

	public Users findUserByUserId(long user_id) {
		return usersDAO.findUserByUserId(user_id);
	}
	
	public Users findUserByProjectId(long project_id) {
		return usersDAO.findUserByProjectId(project_id);
	}

	public List<Users> getAllUsers() {
		return usersDAO.getAllUsers();
	}
	
	public void assignProject(long projectId, long userId) {
		usersDAO.assignProject(projectId, userId);
	}
	
	public void deleteProject(long userId) {
		usersDAO.deleteProject(userId);
	}
	
	public void updateUser(Users user) {
		usersDAO.updateUser(user);
	}
	
	public void assignTaskManager(long userId, long taskId) {
		usersDAO.assignTaskManager(userId, taskId);
	}
}
