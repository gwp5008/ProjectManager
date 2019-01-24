package com.fp.code.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fp.code.model.Task;
import com.fp.code.service.ParentTaskService;
import com.fp.code.service.ProjectService;
import com.fp.code.service.TaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TestTaskController {
	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private TaskService taskService;
	
	@MockBean
	private ProjectService projectService;
	
	@MockBean
	private ParentTaskService parentTaskService;

	private JacksonTester<Task> jsonTask;
	private JacksonTester<ArrayList<Task>> jsonTaskList;

	private Task task1 = new Task();
	
	private Task task2 = new Task();
	
	private ArrayList<Task> taskList = new ArrayList<Task>();

	@Before
	public void setup() {
		// Initializes the JacksonTester
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	public void initializeTasks() {
		task1.setTaskId(1L);
		task1.setTask("task 1");
		task1.setPriority(1);
		task1.setStatus("active");
		
		task2.setTaskId(2L);
		task2.setTask("task 2");
		task2.setPriority(1);
		task2.setStatus("active");
	}
	
	@Test
	public void testGet() throws Exception {
		initializeTasks();

		when(taskService.findTask(anyLong())).thenReturn(task1);

		MockHttpServletResponse response = mockMvc.perform(get("/tasks/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonTask.write(task1).getJson());
	}

	@Test
	public void testGetLastId() throws Exception {
		initializeTasks();
		
		when(taskService.getLastId()).thenReturn(task2.getTaskId());
		
		MockHttpServletResponse response = mockMvc.perform(get("/tasks/").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(Long.parseLong(response.getContentAsString()) == task2.getTaskId());
	}

	@Test
	public void testGetAllTasksByProjectId() throws Exception {
		initializeTasks();
		
		when(taskService.getAllTasksByProjectId(anyLong())).thenReturn(taskList);
		
		MockHttpServletResponse response = mockMvc.perform(get("/tasks/get-tasks-by-projectId/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonTaskList.write(taskList).getJson());
	}
}
