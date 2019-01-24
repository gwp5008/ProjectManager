package com.fp.code.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.fp.code.model.ParentTask;
import com.fp.code.service.ParentTaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentTaskController.class)
public class TestParentTaskController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private ParentTaskService ptService;

	private JacksonTester<ParentTask> jsonParentTask;
	private JacksonTester<ArrayList<ParentTask>> jsonPTList;

	ParentTask firstParent = new ParentTask();
	ParentTask secondParent = new ParentTask();
	ArrayList<ParentTask> ptList = new ArrayList<ParentTask>();

	@Before
	public void setup() {
		// Initializes the JacksonTester
		JacksonTester.initFields(this, new ObjectMapper());
	}

	public void initializeParents() {
		firstParent.setParentId(1L);
		firstParent.setStatus("active");
		firstParent.setParentTask("parent task 1");

		secondParent.setParentId(2L);
		secondParent.setStatus("active");
		secondParent.setParentTask("parent task 2");

		ptList.add(firstParent);
		ptList.add(secondParent);
	}

	@Test
	public void testGetOne() throws Exception {
		initializeParents();

		when(ptService.findParent(anyLong())).thenReturn(firstParent);

		MockHttpServletResponse response = mockMvc.perform(get("/parent-tasks/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonParentTask.write(firstParent).getJson());
	}

	@Test
	public void testGetAll() throws Exception {
		initializeParents();

		when(ptService.getAll()).thenReturn(ptList);

		MockHttpServletResponse response = mockMvc.perform(get("/parent-tasks/").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonPTList.write(ptList).getJson());
	}

	@Test
	public void testGetParentByName() throws Exception {
		initializeParents();

		when(ptService.getParentByName("parent task 1")).thenReturn(firstParent);
		
		MockHttpServletResponse response = mockMvc.perform(get("/parent-tasks/get-pt-by-name/parent task 1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonParentTask.write(firstParent).getJson());
	}
}
