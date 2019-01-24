package com.fp.code.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fp.code.dao.UsersDAO;
import com.fp.code.model.Users;

public class TestUsersService {

	@InjectMocks
	private UsersService usersService;

	@Mock
	private UsersDAO usersDAO;

	@Mock
	private Users user;

	@Mock
	ArrayList<Users> allUsers;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindUserByUserId() {
		when(usersDAO.findUserByUserId(anyLong())).thenReturn(user);

		Users newUser = usersService.findUserByUserId(5L);

		assertNotNull(newUser);
	}

	@Test
	public void testFindUserByProjectId() {
		when(usersDAO.findUserByProjectId(anyLong())).thenReturn(user);

		Users newUser = usersService.findUserByProjectId(5L);

		assertNotNull(newUser);
	}
	
	@Test
	public void testGetAllUsers() {
		when(usersDAO.getAllUsers()).thenReturn(allUsers);
		
		ArrayList<Users> users = (ArrayList<Users>) usersService.getAllUsers();
		
		assertNotNull(users);
	}

	@Test
	public void testAssignProject() {
		doNothing().when(usersDAO).assignProject(anyLong(), anyLong());
		usersService.assignProject(5L, 5L);
		
		verify(usersDAO, times(1)).assignProject(5L, 5L);
	}

	@Test
	public void testDeleteProject() {
		doNothing().when(usersDAO).deleteProject(anyLong());
		usersService.deleteProject(5L);
		
		verify(usersDAO, times(1)).deleteProject(5L);
	}

	@Test
	public void testUpdateUser() {
		doNothing().when(usersDAO).updateUser(user);
		usersService.updateUser(user);
		
		verify(usersDAO, times(1)).updateUser(user);
	}

	@Test
	public void testAssignTaskManager() {
		doNothing().when(usersDAO).assignTaskManager(anyLong(), anyLong());
		usersService.assignTaskManager(5L, 5L);
		
		verify(usersDAO, times(1)).assignTaskManager(5L, 5L);
	}
}
