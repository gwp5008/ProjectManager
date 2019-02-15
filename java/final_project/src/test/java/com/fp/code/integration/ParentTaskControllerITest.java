package com.fp.code.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fp.code.FinalProjectApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FinalProjectApplication.class)
public class ParentTaskControllerITest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetOneParent() throws Exception {
		String parentToGet = new StringBuilder().append("/parent-tasks").append("/{parentId}").toString();

		int parentId = 2;

		mockMvc.perform(get(parentToGet, parentId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
//						content().json("{'parentId': 2, " + "'parentTask': 'parent task 1', " + "'status': 'active'}"))
						content().json("{'parentId': 2, " + "'parentTask': 'parent task 1'"))
				.andDo(print()).andReturn();
	}
}
