package com.fp.code.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fp.code.model.Project;
import com.fp.code.service.ProjectService;
import com.fp.code.service.TaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class TestProjectController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;

	@MockBean
	private TaskService taskService;

	private JacksonTester<Project> jsonProject;
	private JacksonTester<ArrayList<Project>> jsonProjectList;

	private Project project1 = new Project();
	private Project project2 = new Project();
	private ArrayList<Project> projectList = new ArrayList<Project>();

	@Before
	public void setup() {
		// Initializes the JacksonTester
		JacksonTester.initFields(this, new ObjectMapper());
	}

	public void initializeProjects() {
		// String startDate = "2018-01-01";
		// String endDate = "2018-01-01";
		// project1.setStartDate(Date.valueOf(startDate));
		// project1.setEndDate(Date.valueOf(endDate));
		project1.setProjectId(1L);
		project1.setProjectName("project 1");
		project1.setPriority(1);
		project1.setStatus("active");

		project2.setProjectId(2L);
		project2.setProjectName("project 1");
		project2.setPriority(1);
		project2.setStatus("active");

		projectList.add(project1);
		projectList.add(project2);
	}

	@Test
	public void testGetProject() throws Exception {
		initializeProjects();

		when(projectService.findProject(anyString())).thenReturn(project1);

		MockHttpServletResponse response = mockMvc.perform(get("/projects/project 1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonProject.write(project1).getJson());
	}

	@Test
	public void testGetAll() throws Exception {
		initializeProjects();

		when(projectService.getAllProjects()).thenReturn(projectList);

		MockHttpServletResponse response = mockMvc.perform(get("/projects/").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonProjectList.write(projectList).getJson());
	}

	@Test
	public void testGetTaskCount() throws Exception {
		initializeProjects();

		when(taskService.getTaskCount(anyLong())).thenReturn(0L);

		MockHttpServletResponse response = mockMvc.perform(get("/projects/task-count/5").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(Long.parseLong(response.getContentAsString()) == 0L);
	}
	
	@Test
	public void testGetCompletedTaskCount() throws Exception {
		initializeProjects();
		
		when(taskService.getCompletedTaskCount(anyLong())).thenReturn(0L);
		
		MockHttpServletResponse response = mockMvc.perform(get("/projects/completed-task-count/5").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(Long.parseLong(response.getContentAsString()) == 0L);
	}
}
