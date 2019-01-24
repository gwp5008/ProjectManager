package com.fp.code.dao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fp.code.model.ParentTask;
import com.fp.code.model.Users;
import com.fp.code.repository.UsersRepository;

public class TestUsersDAO {

	@InjectMocks
	private UsersDAO usersDAO;

	@Mock
	private UsersRepository usersRepo;

	@Mock
	private Users user;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindUserById() {
		when(usersRepo.findByUserId(anyLong())).thenReturn(user);

		Users newUser = usersDAO.findUserByUserId(13);
		assertNotNull(newUser);
	}

	@Test
	public void testFindUserByProjectId() {
		when(usersRepo.findByProjectId(anyLong())).thenReturn(user);

		Users newUser = usersDAO.findUserByProjectId(13);
		assertNotNull(newUser);
	}

	@Test
	public void testGetAll() {
		when(usersRepo.findAll()).thenReturn(new ArrayList<Users>());

		List<Users> usersList = usersDAO.getAllUsers();
		assertNotNull(usersList);
	}

	@Test
	public void testSaveUser() {
		doNothing().when(usersRepo).assignProject(anyLong(), anyLong());
		usersDAO.assignProject(5L, 5L);

		verify(usersRepo, times(1)).assignProject(5L, 5L);
	}
}
