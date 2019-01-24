package com.fp.code.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fp.code.model.Users;
import com.fp.code.service.ProjectService;
import com.fp.code.service.TaskService;
import com.fp.code.service.UsersService;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class TestUsersController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private UsersService usersService;
	
	@MockBean
	private ProjectService projectService;
	
	@MockBean
	private TaskService taskService;

	private JacksonTester<Users> jsonUsers;
	private JacksonTester<ArrayList<Users>> jsonUsersList;

	private Users user1 = new Users();
	private Users user2 = new Users();
	private ArrayList<Users> usersList = new ArrayList<Users>();

	@Before
	public void setup() {
		// Initializes the JacksonTester
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	public void initializeUsers() {
		user1.setUserId(1L);
		user1.setEmployeeId(123);
		user1.setFirstName("steve");
		user1.setLastName("stevenson");
		user1.setStatus("active");
		
		user2.setUserId(2L);
		user2.setEmployeeId(456);
		user2.setFirstName("bill");
		user2.setLastName("billington");
		user2.setStatus("active");
	}
	
	@Test
	public void testGetByUserId() throws Exception {
		when(usersService.findUserByUserId(anyLong())).thenReturn(user1);
		
		MockHttpServletResponse response = mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonUsers.write(user1).getJson());
	}

	@Test
	public void testGetByProjectId() throws Exception {
		when(usersService.findUserByProjectId(anyLong())).thenReturn(user1);
		
		MockHttpServletResponse response = mockMvc.perform(get("/users/project/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonUsers.write(user1).getJson());
	}

	@Test
	public void testList() throws Exception {
		when(usersService.getAllUsers()).thenReturn(usersList);
		
		MockHttpServletResponse response = mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonUsersList.write(usersList).getJson());
	}
}
