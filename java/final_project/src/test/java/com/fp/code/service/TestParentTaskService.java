package com.fp.code.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.fp.code.dao.ParentTaskDAO;
import com.fp.code.model.ParentTask;

public class TestParentTaskService {

	@InjectMocks
	private ParentTaskService ptService;

	@Mock
	private ParentTaskDAO ptDAO;

	@Mock
	private ParentTask pt;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindParent() {
		when(ptDAO.findParent(anyLong())).thenReturn(pt);

		ParentTask newPT = ptService.findParent(13);
		assertNotNull(newPT);
	}

	@Test
	public void testGetAll() {
		when(ptDAO.getAll()).thenReturn(new ArrayList<ParentTask>());

		List<ParentTask> ptList = ptService.getAll();
		assertNotNull(ptList);
	}

	@Test
	public void testGetParentNumberByName() {
		when(ptDAO.getParentNumberByName(anyString())).thenReturn(anyLong());

		Long ptNumber = ptService.getParentNumberByName("parent task 1");

		assertNotNull(ptNumber);
	}

	@Test
	public void testGetParentByName() {
		when(ptDAO.getParentByName(anyString())).thenReturn(pt);

		ParentTask newTask = ptService.getParentByName("task");

		assertNotNull(newTask);
	}

	@Test
	public void testSaveParentTask() {
		doNothing().when(ptDAO).saveParentTask(pt);
		ptService.saveParentTask(pt);

		verify(ptDAO, times(1)).saveParentTask(pt);
	}
}